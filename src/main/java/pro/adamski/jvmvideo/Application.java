package pro.adamski.jvmvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author akrystian
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "pro.adamski.jvmvideo.repository.jpa")
@EnableSolrRepositories(basePackages = "pro.adamski.jvmvideo.repository.solr")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
