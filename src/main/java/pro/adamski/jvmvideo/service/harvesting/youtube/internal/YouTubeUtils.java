package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.*;
import org.joda.time.Duration;

import java.math.BigInteger;

/**
 * @author akrystian.
 */
public class YouTubeUtils {
    private YouTubeUtils() {
        throw new UnsupportedOperationException("Pure static class!");
    }

    public static SearchResult prepareSearchResult(String videoId, String videoKind) {
        SearchResult searchResult;
        searchResult = new SearchResult().setId(new ResourceId().setKind(videoKind)
                .setVideoId(videoId));
        return searchResult;
    }

    public static com.google.api.services.youtube.model.Video prepareSearchResult(String videoId, VideoStatistics videoStatistics, VideoSnippet snippet) {
        VideoContentDetails contentDetails = new VideoContentDetails().setDuration(Duration.ZERO.toString());
        return new com.google.api.services.youtube.model.Video().setId(videoId).setContentDetails(contentDetails).setSnippet(snippet).setStatistics(videoStatistics);
    }

    public static VideoSnippet prepareVideoSnippet(String title, String description, DateTime publishedAt,
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

    public static VideoStatistics prepareVideoStatistics(BigInteger likeCount, BigInteger dislikeCount,
                                                         BigInteger
                                                                 viewCount) {
        VideoStatistics videoStatistics = new VideoStatistics();
        videoStatistics.setLikeCount(likeCount);
        videoStatistics.setDislikeCount(dislikeCount);
        videoStatistics.setViewCount(viewCount);
        return videoStatistics;
    }
}
