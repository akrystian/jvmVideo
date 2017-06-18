package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author akrystian
 */
@Service
public class YoutubeHarvester {
    private static final long MAX_RESULTS = 50L;
    private static final String REQUESTED_PART = "id,snippet,statistics,contentDetails";

    private final String apiKey;
    private final String applicationName;

    private YouTube youTube;

    @Autowired
    public YoutubeHarvester(@Value("${youtube.apikey}") String apiKey,
                            @Value("${spring.application.name}") String applicationName) {
        this.apiKey = apiKey;
        this.applicationName = applicationName;
    }

    @PostConstruct
    public void setUp() {
        this.youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null
        ).setApplicationName(applicationName).build();
    }

    Collection<Video> harvest(YouTubeChannel channel, long harvestingTime) {
        List<Video> result = new ArrayList<>();
        try {
            List<String> videoIds = harvestIdentifiers(channel, harvestingTime);
            for (String videoId : videoIds) {
                result.addAll(harvestVideo(channel, videoId));
            }
            return result;
        } catch (IOException e) {
            throw new HarvestingException(e);
        }
    }

    private List<Video> harvestVideo(YouTubeChannel channel, String videoId) throws IOException {
        YouTube.Videos.List listVideosRequest = youTube.videos().list(REQUESTED_PART);
        listVideosRequest.setId(videoId);
        listVideosRequest.setMaxResults(MAX_RESULTS);
        listVideosRequest.setKey(apiKey);
        List<com.google.api.services.youtube.model.Video> videos = listVideosRequest.execute().getItems();
        return videos.stream()
                .map(new VideoMapper(channel)::map)
                .collect(Collectors.toList());
    }

    private List<String> harvestIdentifiers(YouTubeChannel channel, long harvestingTime) throws IOException {
        YouTube.Search.List search = youTube.search().list("id");
        search.setKey(apiKey);
        search.setChannelId(channel.getSourceLink());
        search.setMaxResults(MAX_RESULTS);
        search.setPublishedBefore(new DateTime(harvestingTime));
        search.setPublishedAfter(new DateTime(channel.getLastHarvested().toDate()));
        List<SearchResult> searchResultList = search.execute().getItems();
        return searchResultList
                .stream()
                .filter(new VideoFilter())
                .map(SearchResult::getId)
                .map(ResourceId::getVideoId)
                .collect(toList());
    }
}

class VideoFilter implements Predicate<SearchResult> {
    private static final String VIDEO_KIND = "youtube#video";

    @Override
    public boolean test(SearchResult searchResult) {
        return searchResult != null
                && searchResult.getId().getKind() != null
                && searchResult.getId().getKind().equals(VIDEO_KIND);
    }
}

class VideoMapper {
    private final Source source;

    VideoMapper(Source source) {
        this.source = source;
    }

    Video map(final com.google.api.services.youtube.model.Video input) {
        return new Video(input.getId(),
                input.getSnippet().getTitle(),
                input.getSnippet().getDescription(),
                new Date(input.getSnippet().getPublishedAt().getValue()),
                Duration.parse(input.getContentDetails().getDuration()),
                input.getSnippet().getThumbnails().getDefault().getUrl(),
                source);
    }
}
