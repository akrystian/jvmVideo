package pro.adamski.jvmvideo.service.videos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.solr.VideoRepository;

import java.sql.Date;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
public class VideoServiceTest {
    @MockBean
    private VideoRepository videoRepository;

    private VideoService instance;

    private Video videoA = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
            null, null);

    @Before
    public void init() {
        instance = new VideoService(videoRepository);
    }

    @Test
    public void shouldGetVideosSize() {
        //given
        given(videoRepository.count()).willReturn(1L);

        //when
        long videosSize = instance.getVideosSize();

        //then
        assertThat(videosSize, is(1L));
    }

    @Test
    public void shouldGetSingleVideoLink() {
        //given
        final String videoId = "id1";
        given(videoRepository.findOne(videoId)).willReturn(videoA);
        //when
        final String videoLink = instance.getVideoLink(videoId);

        //then
        assertThat(videoLink, is("https://www.youtube.com/watch?v=" + videoId));
    }
}