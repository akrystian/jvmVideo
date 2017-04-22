package pro.adamski.jvmvideo.service;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author akrystian.
 */
public class YouTubeVideoProviderTest {

    @MockBean
    private SourceRepository sourceRepository;

    private YouTubeVideoProvider instance = new YouTubeVideoProvider(null, "application", sourceRepository);



    @Test
    public void shouldThrowExceptionOnMissingApiKey() throws Exception {
        //given
        instance.setUp();
        YouTubeChannel channel = new YouTubeChannel("videoChannel",new DateTime(0L),"videoId");

        //when
        Collection<Video> videos = instance.fetchVideos(channel);

        //then
        assertThat(videos.size(),is(0));
    }

}