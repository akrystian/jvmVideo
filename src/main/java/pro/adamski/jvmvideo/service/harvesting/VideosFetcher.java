package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;

import java.io.IOException;
import java.util.List;

/**
 * @author akrystian.
 */
class VideosFetcher {
    private static final Long MAX_RESULTS = 1L;
    private static final String REQUESTED_PART = "id,snippet,statistics,contentDetails";
    private final YouTube youTube;
    private final String apiKey;

    VideosFetcher(YouTube youTube, String apiKey) {
        this.youTube = youTube;
        this.apiKey = apiKey;
    }

    List<Video> fetch(String videoId) throws IOException {
        YouTube.Videos.List listVideosRequest = youTube.videos().list(REQUESTED_PART);
        listVideosRequest.setId(videoId).setMaxResults(MAX_RESULTS).setKey(apiKey);
        return listVideosRequest.execute().getItems();
    }
}
