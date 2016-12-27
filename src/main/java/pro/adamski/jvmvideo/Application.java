package pro.adamski.jvmvideo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.service.VideoRepository;

import java.net.URL;
import java.time.Duration;
import java.util.Date;

/**
 * @author akrystian
 */
@SpringBootApplication
public class Application{

    @Bean
    CommandLineRunner runner(final VideoRepository repository){
        return args -> {
            Video video = new Video("title", "description", new Date(), Duration.ofMinutes(55), new URL("https://www.youtube.com/watch?v=0drVTNFUqgc"));
            repository.save(video);
        };
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
