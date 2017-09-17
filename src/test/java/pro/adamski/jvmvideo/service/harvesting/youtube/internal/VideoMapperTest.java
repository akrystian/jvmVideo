package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.Video;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static java.math.BigInteger.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.YouTubeUtils.*;

/**
 * @author akrystian.
 */
public class VideoMapperTest {
    @Test
    public void shouldConvertVideo() throws Exception {
        //given
        String videoId = "someVideoId";
        String title = "someTitle";
        String description = "someDescription";
        DateTime publishedAt = new DateTime(2L);
        String url = "someUrl";
        final VideoStatistics videoStatistics = prepareVideoStatistics(valueOf(1L), valueOf(2L), valueOf
                (3L));
        final VideoSnippet videoSnippet = prepareVideoSnippet(title, description, publishedAt, url);
        com.google.api.services.youtube.model.Video result = prepareSearchResult(videoId, videoStatistics,
                videoSnippet);

        //when
        Video actual = VideoMapper.map(null, result);
        //then
        assertThat(actual.getVideoId(), is(videoId));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(2L)));
        assertThat(actual.getThumbnailLink(), is(url));
        assertThat(actual.getStatistic().getLiked(), is(1L));
    }

    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionOnClassCreation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<VideoMapper> constructor = VideoMapper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldConvertVideoWithMissingLikeCount() throws Exception {
        //given
        String videoId = "someVideoId";
        String title = "someTitle";
        String description = "someDescription";
        DateTime publishedAt = new DateTime(2L);
        String url = "someUrl";
        final VideoStatistics videoStatistics = prepareVideoStatistics(null, valueOf(1L),
                valueOf(3L));
        final VideoSnippet videoSnippet = prepareVideoSnippet(title, description, publishedAt, url);
        com.google.api.services.youtube.model.Video result = prepareSearchResult(videoId, videoStatistics,
                videoSnippet);

        //when
        Video actual = VideoMapper.map(null, result);
        //then
        assertThat(actual.getVideoId(), is(videoId));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(2L)));
        assertThat(actual.getThumbnailLink(), is(url));
        assertThat(actual.getStatistic().getLiked(), is(0L));
    }




}