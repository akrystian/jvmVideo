package pro.adamski.jvmvideo.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.ResultSetConverter;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author akrystian
 */
@Service
public class YouTubeVideoProvider {
    public static final Logger log = LoggerFactory.getLogger(YouTubeVideoProvider.class);
    private static final long MAX_RESULTS = 50L;
    private static final String REQUESTED_PART = "id,snippet";
    private static final String REQUESTED_FIELDS = "items(id(kind,videoId),snippet(title,description,publishedAt,thumbnails/default/url))";

    private final String apiKey;
    private final String applicationName;
    private final SourceRepository sourceRepository;

    private YouTube youTube;

    @Autowired
    public YouTubeVideoProvider(@Value("${youtube.apikey}") String apiKey,
                                @Value("${spring.application.name}") String applicationName,
                                SourceRepository sourceRepository) {
        this.apiKey = apiKey;
        this.applicationName = applicationName;
        this.sourceRepository = sourceRepository;
    }

    @PostConstruct
    public void setUp() {
        this.youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null
        ).setApplicationName(applicationName).build();
    }

    public Collection<Video> fetchVideos(YouTubeChannel channel) {
        try {
            YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
            search.setKey(apiKey);
            search.setChannelId(channel.getSourceLink());
            search.setFields(REQUESTED_FIELDS);
            search.setMaxResults(MAX_RESULTS);
            long now = System.currentTimeMillis();
            search.setPublishedBefore(new DateTime(now));
            search.setPublishedAfter(new DateTime(channel.getLastHarvested().toDate()));
            List<SearchResult> searchResultList = search.execute().getItems();
            channel.setLastHarvested(new org.joda.time.DateTime(now));
            sourceRepository.save(channel);
            return new ResultSetConverter(channel).convertVideos(searchResultList);
        }catch (IOException e){
            log.error("Can't fetch channel!",e);
            return Collections.emptyList();
        }
    }
    @SuppressWarnings("unused method parameter")
    public Collection<Video> fetchVideos(Source channel) {
        return Collections.emptyList();
    }
}
