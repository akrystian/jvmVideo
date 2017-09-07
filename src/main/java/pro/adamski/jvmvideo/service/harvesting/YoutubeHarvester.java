package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author akrystian
 */
@Service
public class YoutubeHarvester {
    private final String apiKey;
    private final String applicationName;

    private VideoHarvester videoHarvester;
    private IdentifierHarvester identifierHarvester;

    @Autowired
    public YoutubeHarvester(@Value("${youtube.apikey}") String apiKey,
                            @Value("${spring.application.name}") String applicationName) {
        this.apiKey = apiKey;
        this.applicationName = applicationName;
    }

    @PostConstruct
    public void setUp() {
        final YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null
        ).setApplicationName(applicationName).build();
        this.videoHarvester = new VideoHarvester(youTube, apiKey);
        this.identifierHarvester = new IdentifierHarvester(new IdentifiersFetcher(youTube, apiKey));
    }


    Video harvestVideo(YouTubeChannel channel, String videoId) throws IOException {
        return videoHarvester.harvest(channel, videoId);
    }


    List<String> harvestIdentifiers(YouTubeChannel channel, long harvestingTime) throws IOException {
        return identifierHarvester.harvest(channel, harvestingTime);
    }
}



