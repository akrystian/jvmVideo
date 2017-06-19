package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import org.joda.time.Duration;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.Video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        com.google.api.services.youtube.model.Video result = prepareSearchResult(videoId, title, description, publishedAt, url);
        List<com.google.api.services.youtube.model.Video> results = new ArrayList<>();
        results.add(result);
        //when
        Video actual = instance.map(result);
        //then
        assertThat(actual.getVideoId(), is(videoId));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(2L)));
        assertThat(actual.getThumbnailLink(), is(url));
    }

    private com.google.api.services.youtube.model.Video prepareSearchResult(String videoId, String title, String description, DateTime publishedAt, String url) {
        com.google.api.services.youtube.model.Video result = new com.google.api.services.youtube.model.Video();


        result.setId(videoId);

        VideoSnippet snippet = new VideoSnippet();
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl(url);

        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setPublishedAt(publishedAt);
        snippet.setThumbnails(new ThumbnailDetails().setDefault(thumbnail));

        VideoContentDetails contentDetails = new VideoContentDetails();
        contentDetails.setDuration(Duration.ZERO.toString());


        result.setContentDetails(contentDetails);
        result.setSnippet(snippet);
        return result;
    }


}