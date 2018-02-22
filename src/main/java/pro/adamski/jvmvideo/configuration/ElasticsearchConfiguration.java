package pro.adamski.jvmvideo.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;

/**
 * @author krystian
 */
@Configuration
public class ElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.clustername}")
    private String clustername;

    @Bean
    public Client client() throws Exception {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clustername)
                .build();

        return TransportClient.builder()
                .settings(settings)
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
