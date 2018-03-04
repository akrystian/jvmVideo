package pro.adamski.jvmvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.data.repository.query.QueryLookupStrategy.Key.USE_DECLARED_QUERY;

/**
 * @author akrystian
 */
@SpringBootApplication
@EnableScheduling
@EnableElasticsearchRepositories(basePackages = "pro.adamski.jvmvideo.repository.search",
        queryLookupStrategy = USE_DECLARED_QUERY)
@EnableJpaRepositories(basePackages = "pro.adamski.jvmvideo.repository.jpa")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
