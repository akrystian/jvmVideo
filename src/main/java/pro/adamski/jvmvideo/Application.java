package pro.adamski.jvmvideo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.VideoRepository;
import pro.adamski.jvmvideo.service.YouTubeVideoProvider;

import java.util.Collection;

/**
 * @author akrystian
 */
@SpringBootApplication
public class Application {
    private final YouTubeVideoProvider youTubeVideoProvider;

    @Autowired
    public Application(YouTubeVideoProvider youTubeVideoProvider) {
        this.youTubeVideoProvider = youTubeVideoProvider;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(final VideoRepository repository) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                String channelId = "UC6D58UvAH98IaMVZr80-03g";
                Collection<Video> videos = youTubeVideoProvider.fetchVideosFromChannel(channelId);
                videos.forEach(repository::save);
            }
        };
    }
}
