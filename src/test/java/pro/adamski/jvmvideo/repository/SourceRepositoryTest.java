package pro.adamski.jvmvideo.repository;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SourceRepositoryTest {
    @Autowired
    SourceRepository repository;

    @Test
    public void shouldSaveObject() {
        //given
        final YouTubeChannel channel = new YouTubeChannel("name", new DateTime(DateTime.now()),
                "identifier");
        repository.save(channel);
        //when
        List<Source> all = repository.findAll();


        //then
        assertThat(all.size(), is(1));
    }

}