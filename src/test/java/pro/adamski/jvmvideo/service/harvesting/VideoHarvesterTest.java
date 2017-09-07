package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.joda.time.DateTime;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author akrystian.
 */
public class VideoHarvesterTest {

    @Test
    public void shouldHarvestVideo() throws IOException {
        //given
        final YouTube youTube = new YouTube
                .Builder(new NetHttpTransport(), new JacksonFactory(), null)
                .setApplicationName("test")
                .build();
        VideoHarvester harvester = new VideoHarvester(youTube, "");
        final YouTubeChannel channel = new YouTubeChannel("name", new DateTime(DateTime.now()),
                "identifier");
        //when
        try {
            harvester.harvest(channel, "identifier");
            fail("Exception expected!");
        } catch (GoogleJsonResponseException e) {
            assertThat(e.getDetails().getErrors().get(0).getDomain(), is("usageLimits"));
        }
    }
}