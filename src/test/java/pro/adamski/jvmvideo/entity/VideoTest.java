package pro.adamski.jvmvideo.entity;

import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Date;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class VideoTest {

    private YouTubeChannel channel = new YouTubeChannel("name", new DateTime(DateTime.now()),
            "identifier");
    private Video videoA = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg", channel, null);
    private Video videoB = new Video(
            "id1",
            "title2",
            "description1",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg", channel, null);
    private Video videoC = new Video(
            "id1",
            "title2",
            "description1",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg", channel, null);

    @Test
    public void shouldReturnFields() throws Exception {
        //that
        assertThat(videoA.getTitle(), is("title2"));
        assertThat(videoA.getDescription(), is("description2"));
        assertThat(videoA.getPublishDate(), is(new Date(0L)));
        assertThat(videoA.getDuration(), is(Duration.ofMinutes(552)));
        assertThat(videoA.getThumbnailLink(), is("https://i.ytimg.com/vi/zQll41ha5_g/default.jpg"));
        assertThat(videoA.getSource(), is(channel));
        assertThat(videoA.getVideoLink(), is("https://www.youtube.com/watch?v=id1"));
    }

    @Test
    public void shouldSuccessfullyCompareTwoInstances() {
        //then
        assertThat(videoB, is(videoC));
    }
}