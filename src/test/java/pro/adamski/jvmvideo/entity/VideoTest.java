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


    @Test
    public void shouldEqualsAndHashcodeProperlyOnTheSameObjects() {
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

}