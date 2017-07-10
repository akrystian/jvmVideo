package pro.adamski.jvmvideo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Video;

import java.sql.Date;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

}