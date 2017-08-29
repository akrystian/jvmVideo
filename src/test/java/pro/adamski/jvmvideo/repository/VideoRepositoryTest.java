package pro.adamski.jvmvideo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;

import java.sql.Date;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VideoRepositoryTest {
    @Autowired
    VideoRepository repository;



    @Test
    public void shouldFindSingle() {
        //given
        final Video videoA = new Video(
                "id1",
                "title2",
                "description2",
                new Date(0L),
                Duration.ofMinutes(552),
                "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
                null, null);
        repository.save(videoA);

        //when
        List<Video> all = repository.findAll();


        //then
        assertThat(all.size(),is(1));
    }

    @Test
    public void shouldFindSingleVideoByStatistics() {
        //given
        final Video videoA = new Video(
                "id1",
                "title1",
                "description1",
                new Date(0L),
                Duration.ofMinutes(552),
                "https://i.ytimg.com/vi/zQll41ha5_g/default1.jpg",
                null, new VideoStatistic(2, 1, 2));
        final Video videoB = new Video(
                "id2",
                "title2",
                "description2",
                new Date(0L),
                Duration.ofMinutes(552),
                "https://i.ytimg.com/vi/zQll41ha5_g/default2.jpg",
                null, new VideoStatistic(1, 2, 1));
        repository.save(videoA);
        repository.save(videoB);

        //when
        final PageRequest pageable = new PageRequest(0, 2, new Sort(new Order(DESC,
                "statistic_views"
        )));
        List<Video> all = repository.findAll(pageable).getContent();


        //then
        assertThat(all.get(0).getVideoId(), is("id1"));
        assertThat(all.get(1).getVideoId(), is("id2"));
    }

}