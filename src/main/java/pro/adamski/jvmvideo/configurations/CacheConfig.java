package pro.adamski.jvmvideo.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static pro.adamski.jvmvideo.classes.CacheControlGenerator.generate;

/**
 * @author akrystian.
 */
@Configuration
public class CacheConfig extends WebMvcConfigurerAdapter {

    private final int cachePeriod;

    public CacheConfig(@Value("${jvmvideo.cache-period}") int cachePeriod) {
        this.cachePeriod = cachePeriod;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(generate(cachePeriod));
    }
}
