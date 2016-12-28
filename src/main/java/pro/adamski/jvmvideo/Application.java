package pro.adamski.jvmvideo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.service.VideoRepository;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author akrystian
 */
@SpringBootApplication
public class Application{

    @Bean
    CommandLineRunner runner(final VideoRepository repository){
        return args -> {
            List<Video> videos = Arrays.asList(
                    new Video("title", "description", new Date(),
                            Duration.ofMinutes(55), new URL("https://www.youtube.com/watch?v=0drVTNFUqgc")),
                    new Video("title2", "description2", new Date(),
                            Duration.ofMinutes(552), new URL("https://www.youtube.com/watch?v=0drVTNFUqgc"))
            );
            videos.forEach(repository::save);
        };
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
