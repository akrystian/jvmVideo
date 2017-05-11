package pro.adamski.jvmvideo.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.ResultSetConverter;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author akrystian
 */
@Service
public class YoutubeHarvesterService {
    private static final long MAX_RESULTS = 50L;
    private static final String REQUESTED_PART = "id,snippet";
    private static final String REQUESTED_FIELDS = "items(id(kind,videoId),snippet(title,description,publishedAt,thumbnails/default/url))";

    private final String apiKey;
    private final String applicationName;

    private YouTube youTube;

    @Autowired
    public YoutubeHarvesterService(@Value("${youtube.apikey}") String apiKey,
                                   @Value("${spring.application.name}") String applicationName) {
        this.apiKey = apiKey;
        this.applicationName = applicationName;
    }

    @PostConstruct
    public void setUp() {
        this.youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null
        ).setApplicationName(applicationName).build();
    }

    public Collection<Video> harvest(YouTubeChannel channel, long harvestingTime) {
        try {
            YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
            search.setKey(apiKey);
            search.setChannelId(channel.getSourceLink());
            search.setFields(REQUESTED_FIELDS);
            search.setMaxResults(MAX_RESULTS);
            search.setPublishedBefore(new DateTime(harvestingTime));
            search.setPublishedAfter(new DateTime(channel.getLastHarvested().toDate()));
            List<SearchResult> searchResultList = search.execute().getItems();
            return new ResultSetConverter(channel).convertVideos(searchResultList);
        } catch (IOException e) {
            throw new HarvestingException(e);
        }
    }
}
