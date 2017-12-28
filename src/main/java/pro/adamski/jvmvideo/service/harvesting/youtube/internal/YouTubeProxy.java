package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class YouTubeProxy {
    private static final Long MAX_RESULTS = 50L;
    private static final Long SINGLE = 1L;
    private static final String REQUESTED_PART = "id";
    private final String apiKey;
    private YouTube youTube;

    protected YouTubeProxy(String apiKey, YouTube youTube) {
        this.apiKey = apiKey;
        this.youTube = youTube;
    }

    @Autowired
    public YouTubeProxy(@Value("${spring.application.name}") String appName,
                        @Value("${youtube.apikey}") String apiKey) {
        this.youTube = build(appName);
        this.apiKey = apiKey;
    }


    private static YouTube build(String appName) {
        return new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                null
        ).setApplicationName(appName).build();
    }

    public List<SearchResult> listIdsFromChannel(String channelId, DateTime since, DateTime until)
            throws IOException {
        List<SearchResult> result = new ArrayList<>();
        SearchListResponse response;
        String pageToken = null;
        do {
            response = getPageSearchListResponse(channelId, since, until, pageToken);
            final List<SearchResult> items = response.getItems();
            result.addAll(items);
            pageToken = response.getNextPageToken();
        } while (pageToken != null);

        return result;
    }

    private SearchListResponse getPageSearchListResponse(String channelId, DateTime since, DateTime
            until, String pageToken) throws IOException {
        YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
        search.setKey(apiKey)
                .setChannelId(channelId)
                .setMaxResults(MAX_RESULTS)
                .setPublishedBefore(until)
                .setPublishedAfter(since);
        if (pageToken != null) {
            search.setPageToken(pageToken);
        }
        return search.execute();
    }

    public List<Video> getVideo(String videoId, RequestedPart requestedPart) throws IOException {
        YouTube.Videos.List listVideosRequest = youTube.videos().list(requestedPart.parts());
        listVideosRequest.setKey(apiKey)
                .setId(videoId)
                .setMaxResults(SINGLE);
        return listVideosRequest.execute().getItems();
    }
}