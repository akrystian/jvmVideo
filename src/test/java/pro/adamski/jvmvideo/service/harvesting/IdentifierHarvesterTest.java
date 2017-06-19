package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.joda.time.DateTime;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author akrystian.
 */
public class IdentifierHarvesterTest {

    @Test
    public void shouldHarvestIdentifiers() throws IOException {
        //given
        final YouTube youTube = new YouTube
                .Builder(new NetHttpTransport(), new JacksonFactory(), null)
                .setApplicationName("test")
                .build();
        IdentifierHarvester harvester = new IdentifierHarvester(youTube, "", 1L);
        final YouTubeChannel channel = new YouTubeChannel("name", new DateTime(DateTime.now()),
                "identifier");

        try {
            //when
            harvester.harvest(channel, System.currentTimeMillis());
            fail("Exception expected!");
        } catch (GoogleJsonResponseException e) {
            //then
            assertThat(e.getDetails().getErrors().get(0).getDomain(), is("usageLimits"));
        }
    }
}