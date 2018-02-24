package pro.adamski.jvmvideo.service.harvesting;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.jpa.SourceRepository;
import pro.adamski.jvmvideo.repository.jpa.VideoRepository;
import pro.adamski.jvmvideo.repository.search.SearchVideoRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;


/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
public class HarvesterServiceTest {

    @MockBean
    private SourceRepository sourceRepository;

    @MockBean
    private YoutubeHarvester youtubeHarvester;

    @MockBean
    private VideoRepository videoRepository;

    @MockBean
    private SearchVideoRepository videoSearchRepository;

    private HarvesterService instance;

    private Video videoA = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552).getSeconds(),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
            null);

    @Before
    public void init() {
        instance = new HarvesterService(youtubeHarvester, sourceRepository,
                videoRepository, videoSearchRepository);
        instance.init();
    }

    @Test
    public void shouldHarvestChannel() throws IOException {
        //given
        given(sourceRepository.findAll()).willReturn(Collections.singletonList(
                new YouTubeChannel("name", new DateTime(DateTime.now()),
                        "identifier")));

        given(youtubeHarvester.harvestVideo(any(), anyString())).willReturn(videoA);
        given(youtubeHarvester.harvestIdentifiers(any(YouTubeChannel.class), anyLong())).willReturn
                (Collections.singletonList("identifier"));
        //when
        instance.harvestAllSources();

        //then
        then(videoRepository).should(times(1)).save(any(Video.class));
    }


}