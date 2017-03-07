package pro.adamski.jvmvideo.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.entity.Video;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author krystian
 */
@Service
public class YouTubeVideoProvider {
    private static final long MAX_RESULTS = 50L;
    private static final String REQUESTED_PART = "id,snippet";
    private static final String REQUESTED_FIELDS = "items(id(kind,videoId),snippet(title,description,publishedAt,thumbnails/default/url))";
    private static final String VIDEO_KIND = "youtube#video";

    private YouTube youTube;

    private final String apiKey;
    private final String applicationName;

    public YouTubeVideoProvider(@Value("${youtube.apikey}") String apiKey,
                                @Value("${spring.application.name}")String applicationName) {
        this.apiKey = apiKey;
        this.applicationName = applicationName;
    }

    @PostConstruct
    public void setUp(){
        this.youTube = new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                request -> {}
        ).setApplicationName(applicationName).build();
    }

    public Collection<Video> fetchVideosFromChannel(final String channelId)
            throws IOException {
        YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
        search.setKey(apiKey);
        search.setChannelId(channelId);
        search.setFields(REQUESTED_FIELDS);
        search.setMaxResults(MAX_RESULTS);

        List<SearchResult> searchResultList = search.execute().getItems();

        return convertVideos(searchResultList);
    }

    private List<Video> convertVideos(List<SearchResult> searchResultList){
        List<Video> videos = new ArrayList<>();
        for (SearchResult video : searchResultList) {
            convertVideo(videos, video);
        }
        return videos;
    }

    private void convertVideo(List<Video> videos, SearchResult video){
        ResourceId rId = video.getId();
        if (rId != null && rId.getKind() != null && rId.getKind().equals(VIDEO_KIND)) {
            videos.add(mapToVideo(video));
        }
    }

    private Video mapToVideo(SearchResult singleVideo) {
        return new Video(singleVideo.getId().getVideoId(),
                singleVideo.getSnippet().getTitle(),
                singleVideo.getSnippet().getDescription(),
                new Date(singleVideo.getSnippet().getPublishedAt().getValue()),
                null,
                singleVideo.getSnippet().getThumbnails().getDefault().getUrl());
    }


}
