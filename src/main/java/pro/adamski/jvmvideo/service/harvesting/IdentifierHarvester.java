package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
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
    private static final String REQUESTED_PART = "id";
    private final YouTube youTube;
    private final String apiKey;
    private final Long maxResults;

    IdentifierHarvester(YouTube youTube, String apiKey, Long maxResults) {
        this.youTube = youTube;
        this.apiKey = apiKey;
        this.maxResults = maxResults;
    }

    List<String> harvest(YouTubeChannel channel, long harvestingTime) throws IOException {
        YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
        search.setKey(apiKey);
        search.setChannelId(channel.getSourceLink());
        search.setMaxResults(maxResults);
        search.setPublishedBefore(new DateTime(harvestingTime));
        search.setPublishedAfter(new DateTime(channel.getLastHarvested().toDate()));
        List<SearchResult> searchResultList = search.execute().getItems();
        return searchResultList.stream().filter(new VideoFilter()).map(SearchResult::getId)
                .map(ResourceId::getVideoId).collect(toList());
    }
}

class VideoFilter implements Predicate<SearchResult> {
    private static final String VIDEO_KIND = "youtube#video";

    @Override
    public boolean test(SearchResult searchResult) {
        return searchResult != null && searchResult.getId().getKind() != null
                && searchResult.getId().getKind().equals(VIDEO_KIND);
    }
}