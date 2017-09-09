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
    private static final String ALL = "id,snippet,statistics,contentDetails";
    private static final String STATS = "statistics";
    private final YouTube youTube;
    private final String apiKey;

    VideoHarvester(YouTube youTube, String apiKey) {
        this.youTube = youTube;
        this.apiKey = apiKey;

    }

    Video harvest(YouTubeChannel channel, String videoId) throws IOException {
        final com.google.api.services.youtube.model.Video input = getVideo(videoId, ALL);
        return new VideoMapper(channel).map(input);
    }

    VideoStatistic harvestStats(String videoId) throws IOException {
        final com.google.api.services.youtube.model.Video input = getVideo(videoId, STATS);
        return VideoMapper.map(input.getStatistics());
    }

    private com.google.api.services.youtube.model.Video getVideo(String videoId, String requestedPart) throws IOException {
        YouTube.Videos.List listVideosRequest = youTube.videos().list(requestedPart);
        listVideosRequest.setId(videoId);
        listVideosRequest.setMaxResults(MAX_RESULTS);
        listVideosRequest.setKey(apiKey);
        List<com.google.api.services.youtube.model.Video> videos = listVideosRequest.execute().getItems();
        if (videos.size() != 1) {
            throw new HarvestingException("Unsupported number of records! Number : " + videos.size() + "!");
        }
        return videos.get(0);
    }
}

class VideoMapper {
    private final Source source;

    VideoMapper(Source source) {
        this.source = source;
    }

    static VideoStatistic map(VideoStatistics statistics) {
        return new VideoStatistic(
                statistics.getViewCount().longValue(),
                statistics.getLikeCount().longValue(),
                statistics.getDislikeCount().longValue());
    }

    Video map(final com.google.api.services.youtube.model.Video input) {
        final VideoStatistics statistics = input.getStatistics();
        VideoStatistic statistic = map(statistics);
        return new Video(input.getId(),
                input.getSnippet().getTitle(),
                input.getSnippet().getDescription(),
                new Date(input.getSnippet().getPublishedAt().getValue()),
                Duration.parse(input.getContentDetails().getDuration()),
                input.getSnippet().getThumbnails().getDefault().getUrl(),
                source, statistic);
    }
}