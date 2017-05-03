package pro.adamski.jvmvideo.service;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;

import java.util.Collection;

/**
 * @author akrystian.
 */
public class YoutubeHarvesterServiceServiceTest {

    @MockBean
    private SourceRepository sourceRepository;

    private YoutubeHarvesterService instance = new YoutubeHarvesterService("", "app");



    @Test(expected = HarvestingException.class)
    public void shouldThrowExceptionOnMissingApiKey() throws Exception {
        //given
        instance.setUp();
        YouTubeChannel channel = new YouTubeChannel("videoChannel",new DateTime(0L),"videoId");

        //then
        Collection<Video> videos = instance.harvest(channel,System.nanoTime());
    }

}