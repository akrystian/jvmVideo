package pro.adamski.jvmvideo.entity;

import org.junit.Test;

import java.sql.Date;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class VideoTest {
    private Video videoA = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg");
    private Video videoB = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(552),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg");
    private Video videoC = new Video(
            "id1",
            "title2",
            "description2",
            new Date(0L),
            Duration.ofMinutes(551),
            "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg");
    private Video videoD = new Video();

    @Test
    public void shouldEqualsAndHashcodeProperlyOnTheSameObjects() {
        //then
        assertThat(videoA.equals(videoA),is(true));
        assertThat(videoA.hashCode(),is(videoA.hashCode()));
    }

    @Test
    public void shouldEqualsAndHashcodeProperlyOnTheIdenticalObjects() {
        //then
        assertThat(videoA.equals(videoB),is(true));
        assertThat(videoA.hashCode(),is(videoB.hashCode()));
    }

    @Test
    public void shouldEqualsAndHashcodeProperlyOnTheDifferentObjects() {
        //that
        assertThat(videoA.equals(videoC),is(false));
        assertThat(videoA.hashCode(),is(not(videoC.hashCode())));
    }

    @Test
    public void shouldEqualsAndHashcodeProperlyOnTheDifferentObjectsOneIsNotInitializedFully() {
        //that
        assertThat(videoA.equals(videoD),is(false));
        assertThat(videoA.hashCode(),is(not(videoD.hashCode())));
    }

    @Test
    public void shouldReturnFields() throws Exception {
        //that
        assertThat(videoA.getId(),is("id1"));
        assertThat(videoA.getTitle(),is("title2"));
        assertThat(videoA.getDescription(),is("description2"));
        assertThat(videoA.getPublishDate(),is(new Date(0L)));
        assertThat(videoA.getDuration(),is(Duration.ofMinutes(552)));
        assertThat(videoA.getThumbnailLink(),is("https://i.ytimg.com/vi/zQll41ha5_g/default.jpg"));
    }
}