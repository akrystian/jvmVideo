package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;

import java.sql.Date;
import java.time.Duration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author akrystian.
 */
public class YoutubeHarvesterTest {

    @MockBean
    private SourceRepository sourceRepository;

    private YoutubeHarvester instance = new YoutubeHarvester("", "app");


    @Test
    public void shouldThrowMissingApiKeyExceptionOnHarvestIdentifiers() throws Exception {
        //given
        instance.setUp();
        YouTubeChannel channel = new YouTubeChannel("videoChannel", new DateTime(0L), "videoId");

        try {
            instance.harvestIdentifiers(channel, System.nanoTime());
            fail("Exception expected!");
        } catch (GoogleJsonResponseException e) {
            assertThat(e.getDetails().getErrors().get(0).getDomain(), is("usageLimits"));
        }
    }

    @Test
    public void shouldThrowMissingApiKeyExceptionOnHarvestVideo() throws Exception {
        //given
        instance.setUp();
        YouTubeChannel channel = new YouTubeChannel("videoChannel", new DateTime(0L), "videoId");

        try {
            instance.harvestVideo(channel, "vN08or_jY6c");
            fail("Exception expected!");
        } catch (GoogleJsonResponseException e) {
            assertThat(e.getDetails().getErrors().get(0).getDomain(), is("usageLimits"));
        }
    }

    @Test
    public void shouldUpdateVideoStats() throws Exception {
        //given
        instance.setUp();
        final Video videoA = new Video(
                "id1",
                "title1",
                "description1",
                new Date(0L),
                Duration.ofMinutes(552),
                "https://i.ytimg.com/vi/zQll41ha5_g/default1.jpg",
                null, new VideoStatistic(2, 1, 2));
        try {
            instance.updateStats(videoA);
            fail("Exception expected!");
        } catch (GoogleJsonResponseException e) {
            assertThat(e.getDetails().getErrors().get(0).getDomain(), is("usageLimits"));
        }
    }
}