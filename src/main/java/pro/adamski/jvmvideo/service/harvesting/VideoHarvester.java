package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoStatistics;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.util.List;

/**
 * @author akrystian.
 */
class VideoHarvester {
    private static final Long MAX_RESULTS = 1L;
    private static final String REQUESTED_PART = "id,snippet,statistics,contentDetails";
    private final YouTube youTube;
    private final String apiKey;

    VideoHarvester(YouTube youTube, String apiKey) {
        this.youTube = youTube;
        this.apiKey = apiKey;

    }

    Video harvest(YouTubeChannel channel, String videoId) throws IOException {
        YouTube.Videos.List listVideosRequest = youTube.videos().list(REQUESTED_PART);
        listVideosRequest.setId(videoId);
        listVideosRequest.setMaxResults(MAX_RESULTS);
        listVideosRequest.setKey(apiKey);
        List<com.google.api.services.youtube.model.Video> videos = listVideosRequest.execute().getItems();
        if (videos.size() != 1) {
            throw new HarvestingException("Unsupported number of records! Number : " + videos.size() + "!");
        }
        return new VideoMapper(channel).map(videos.get(0));
    }
}

class VideoMapper {
    private final Source source;

    VideoMapper(Source source) {
        this.source = source;
    }

    Video map(final com.google.api.services.youtube.model.Video input) {
        final VideoStatistics statistics = input.getStatistics();
        VideoStatistic statistic = new VideoStatistic(
                statistics.getViewCount().longValue(),
                statistics.getLikeCount().longValue(),
                statistics.getDislikeCount().longValue());
        return new Video(input.getId(),
                input.getSnippet().getTitle(),
                input.getSnippet().getDescription(),
                new Date(input.getSnippet().getPublishedAt().getValue()),
                Duration.parse(input.getContentDetails().getDuration()),
                input.getSnippet().getThumbnails().getDefault().getUrl(),
                source, statistic);
    }
}