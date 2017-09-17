package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.service.harvesting.youtube.YouTubeService;
import pro.adamski.jvmvideo.service.harvesting.youtube.internal.YouTubeProxy;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.math.BigInteger.valueOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pro.adamski.jvmvideo.service.harvesting.youtube.YouTubeService.VIDEO_KIND;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart.ALL;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart.STATS;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.YouTubeUtils.*;

/**
 * @author akrystian.
 */
public class YouTubeServiceTest {
    private YouTubeProxy youTubeProxy;
    private YouTubeService underTest;

    @Before
    public void init() {
        youTubeProxy = mock(YouTubeProxy.class);
        underTest = new YouTubeService(youTubeProxy);
    }

    @Test
    public void shouldHarvestIdsFromChannel() throws Exception {
        //given
        final String videoId = "videoId";
        YouTubeChannel channel = new YouTubeChannel("videoChannel", new DateTime(0L), videoId);
        final List<SearchResult> results = Collections.singletonList(prepareSearchResult(videoId, VIDEO_KIND));
        when(youTubeProxy.listIdsFromChannel(
                anyString(),
                any(com.google.api.client.util.DateTime.class),
                any(com.google.api.client.util.DateTime.class)
        )).thenReturn(results);

        //when
        final List<String> strings = underTest.harvestIdsFromChannel(channel, System.nanoTime());

        //then
        assertThat(strings.size(), is(1));
        assertThat(strings.get(0), is(videoId));
    }

    @Test
    public void shouldHarvestVideoFromChannel() throws IOException {
        //given
        String videoId = "videoId";
        String title = "someTitle";
        String description = "someDescription";
        com.google.api.client.util.DateTime publishedAt = new com.google.api.client.util.DateTime(2L);
        String url = "someUrl";
        final VideoStatistics videoStatistics = prepareVideoStatistics(valueOf(1L), valueOf(2L), valueOf
                (3L));
        final VideoSnippet videoSnippet = prepareVideoSnippet(title, description, publishedAt, url);
        com.google.api.services.youtube.model.Video video = prepareSearchResult(videoId, videoStatistics,
                videoSnippet);

        YouTubeChannel channel = new YouTubeChannel("videoChannel", new DateTime(0L), videoId);
        final List<com.google.api.services.youtube.model.Video> videos = Collections.singletonList(video);

        when(youTubeProxy.getVideo(videoId, ALL)).thenReturn(videos);

        //when
        final Video result = underTest.harvestVideoFromChannel(channel, "videoId");

        //then
        assertThat(result.getVideoId(), is(videoId));
        assertThat(result.getDescription(), is(description));
    }

    @Test
    public void shouldUpdateStats() throws IOException {
        //given
        String videoId = "videoId";
        String title = "someTitle";
        String description = "someDescription";
        com.google.api.client.util.DateTime publishedAt = new com.google.api.client.util.DateTime(2L);
        String url = "someUrl";
        final VideoStatistics videoStatistics = prepareVideoStatistics(valueOf(3L), valueOf(4L), valueOf
                (5L));
        final VideoSnippet videoSnippet = prepareVideoSnippet(title, description, publishedAt, url);
        com.google.api.services.youtube.model.Video actual = prepareSearchResult(videoId, videoStatistics,
                videoSnippet);

        YouTubeChannel channel = new YouTubeChannel("videoChannel", new DateTime(0L), videoId);
        final List<com.google.api.services.youtube.model.Video> videos = Collections.singletonList(actual);
        when(youTubeProxy.getVideo(videoId, STATS)).thenReturn(videos);

        VideoStatistic videoStatistics2 = new VideoStatistic(1L, 2L, 3L);
        final Video old = new Video(videoId, title, description, new Date(1L), Duration.ZERO, "",
                channel, videoStatistics2);

        //when
        final Video result = underTest.updateStats(old);

        //then
        assertThat(result.getStatistic().getDisliked(), is(4L));
    }


}