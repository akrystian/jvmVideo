package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.services.youtube.model.*;
import org.joda.time.DateTime;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * @author akrystian.
 */
public class VideoHarvesterTest {

    @Test
    public void shouldHarvestVideo() throws IOException {
        //given
        final VideosFetcher videosFetcher = mock(VideosFetcher.class);
        final String id = "id";
        final String title = "Title";
        final String description = "Description";
        final String duration = Duration.ZERO.toString();
        final String url = "url";
        final com.google.api.client.util.DateTime publishedAt = new com.google.api.client.util.DateTime
                (0L);
        final Video youtubeVideo = prepareVideo(id, title, description, duration, url,
                publishedAt);
        given(videosFetcher.fetch(anyString())).willReturn(singletonList(youtubeVideo));
        final VideoHarvester harvester = new VideoHarvester(videosFetcher);
        final YouTubeChannel channel = new YouTubeChannel("name", new DateTime(DateTime.now()),
                "identifier");
        //when
        final pro.adamski.jvmvideo.entity.Video actual = harvester.harvest(channel, "identifier");

        //then
        assertThat(actual.getVideoId(), is(id));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(publishedAt.getValue())));
        assertThat(actual.getThumbnailLink(), is(url));
    }

    private Video prepareVideo(String id, String title, String description, String duration, String url,
                               com.google.api.client.util.DateTime publishedAt) {
        Video video;
        video = new Video();
        video.setId(id);
        video.setContentDetails(new VideoContentDetails().setDuration(duration));
        final VideoSnippet snippet = new VideoSnippet().setTitle(title).setDescription(description)
                .setPublishedAt(publishedAt).setThumbnails(new
                        ThumbnailDetails().setDefault(new
                        Thumbnail().setUrl(url)));
        video.setSnippet(snippet);
        return video;
    }
}