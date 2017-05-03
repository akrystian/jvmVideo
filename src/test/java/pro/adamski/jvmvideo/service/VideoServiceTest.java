package pro.adamski.jvmvideo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.VideoRepository;

import java.sql.Date;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
public class VideoServiceTest {
    @MockBean
    private VideoRepository videoRepository;

    VideoService instance;

    private Video videoA = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
            null);

    @Before
    public void init(){
        instance = new VideoService(videoRepository);
    }

    @Test
    public void shouldGetVideosSize() {
        //given
        given(videoRepository.count()).willReturn(1L);

        //when
        long videosSize = instance.getVideosSize();

        //then
        assertThat(videosSize,is(1L));
    }

    @Test
    public void shouldGetSinglePage() {
        //given
        given(videoRepository.findAllByOrderByPublishDateDesc(any(Pageable.class)))
                .willReturn(new PageImpl<>(Collections.singletonList(videoA)));

        //when
        List<Video> videosPage = instance.getVideosPage(1, 1);

        //then
        assertThat(videosPage.size(),is(1));
    }
}