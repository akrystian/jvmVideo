package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.*;
import org.joda.time.Duration;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.Video;

import java.math.BigInteger;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class VideoMapperTest {
    private VideoMapper instance = new VideoMapper(null);


    @Test
    public void shouldConvertVideo() throws Exception {
        //given
        String videoId = "someVideoId";
        String title = "someTitle";
        String description = "someDescription";
        DateTime publishedAt = new DateTime(2L);
        String url = "someUrl";
        final VideoStatistics videoStatistics = prepareVideoStatistics(1L, 2L, 3L);
        final VideoSnippet videoSnippet = prepareVideoSnippet(title, description, publishedAt, url);
        com.google.api.services.youtube.model.Video result = prepareSearchResult(videoId, url, videoStatistics,
                videoSnippet);

        //when
        Video actual = instance.map(result);
        //then
        assertThat(actual.getVideoId(), is(videoId));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(2L)));
        assertThat(actual.getThumbnailLink(), is(url));
        assertThat(actual.getStatistic().getLiked(), is(1L));
    }

    private com.google.api.services.youtube.model.Video prepareSearchResult(String videoId, String url, VideoStatistics videoStatistics, VideoSnippet snippet) {
        com.google.api.services.youtube.model.Video result = new com.google.api.services.youtube.model.Video();


        result.setId(videoId);




        VideoContentDetails contentDetails = new VideoContentDetails();
        contentDetails.setDuration(Duration.ZERO.toString());


        result.setContentDetails(contentDetails);
        result.setSnippet(snippet);

        result.setStatistics(videoStatistics);

        return result;
    }

    private VideoSnippet prepareVideoSnippet(String title, String description, DateTime publishedAt,
                                             String url) {
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl(url);
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setPublishedAt(publishedAt);
        snippet.setThumbnails(new ThumbnailDetails().setDefault(thumbnail));
        return snippet;
    }

    private VideoStatistics prepareVideoStatistics(Long likeCount, Long dislikeCount, Long
            viewCount) {
        VideoStatistics videoStatistics = new VideoStatistics();
        videoStatistics.setLikeCount(BigInteger.valueOf(likeCount));
        videoStatistics.setDislikeCount(BigInteger.valueOf(dislikeCount));
        videoStatistics.setViewCount(BigInteger.valueOf(viewCount));
        return videoStatistics;
    }


}