package pro.adamski.jvmvideo.configurations;


import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author akrystian.
 */
@Configuration
@EnableSolrRepositories(basePackages = "pro.adamski.jvmvideo.repository.solr", multicoreSupport = true)
public class SolrConfig {

    @Bean
    public SolrClient solrServer() {
        EmbeddedSolrServerFactory factory;
        try {
            factory = new EmbeddedSolrServerFactory("classpath:solr.config/");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return factory.getSolrClient();
    }

    @Bean
    public SolrOperations solrTemplate() {
        return new SolrTemplate(solrServer());
    }
}
