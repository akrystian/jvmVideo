package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author akrystian.
 */
class IdentifierHarvester {
    private final IdentifiersFetcher identifiersFetcher;

    IdentifierHarvester(IdentifiersFetcher identifiersFetcher) {
        this.identifiersFetcher = identifiersFetcher;
    }

    List<String> harvest(YouTubeChannel channel, long harvestingTime) throws IOException {
        final String sourceLink = channel.getSourceLink();
        final DateTime publishedBefore = new DateTime(harvestingTime);
        final DateTime publishedAfter = new DateTime(channel.getLastHarvested().toDate());
        List<SearchResult> searchResultList = identifiersFetcher
                .fetch(sourceLink, publishedBefore, publishedAfter);
        return searchResultList.stream().filter(new VideoFilter()).map(SearchResult::getId)
                .map(ResourceId::getVideoId).collect(toList());
    }
}

class VideoFilter implements Predicate<SearchResult> {
    static final String VIDEO_KIND = "youtube#video";

    @Override
    public boolean test(SearchResult searchResult) {
        return searchResult != null
                && searchResult.getId() != null
                && searchResult.getId().getKind() != null
                && searchResult.getId().getKind().equals(VIDEO_KIND);
    }
}