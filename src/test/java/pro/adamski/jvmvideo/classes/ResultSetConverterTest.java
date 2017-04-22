package pro.adamski.jvmvideo.classes;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.*;
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
public class ResultSetConverterTest {
    private ResultSetConverter instance = new ResultSetConverter(null);


    @Test
    public void shouldConvertVideo() throws Exception {
        //given
        String videoId = "someVideoId";
        String title = "someTitle";
        String description = "someDescription";
        DateTime publishedAt = new DateTime(2L);
        String url = "someUrl";
        SearchResult result = prepareSearchResult(videoId, title, description, publishedAt, url);
        List<SearchResult> results = new ArrayList<>();
        results.add(result);
        //when
        List<Video> videos = instance.convertVideos(results);
        //then
        assertThat(videos.size(), is(1));
        Video actual = videos.get(0);
        assertThat(actual.getId(), is(videoId));
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getDescription(), is(description));
        assertThat(actual.getPublishDate(), is(new Date(2L)));
        assertThat(actual.getThumbnailLink(), is(url));
    }

    private SearchResult prepareSearchResult(String videoId, String title, String description, DateTime publishedAt, String url) {
        SearchResult result = new SearchResult();

        ResourceId resourceId = new ResourceId();
        resourceId.setKind(ResultSetConverter.VIDEO_KIND);
        resourceId.setVideoId(videoId);
        result.setId(resourceId);

        SearchResultSnippet snippet = new SearchResultSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setPublishedAt(publishedAt);

        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setUrl(url);
        snippet.setThumbnails(new ThumbnailDetails().setDefault(thumbnail));
        result.setSnippet(snippet);
        return result;
    }

}