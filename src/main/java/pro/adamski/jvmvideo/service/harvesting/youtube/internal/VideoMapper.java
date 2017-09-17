package pro.adamski.jvmvideo.service.harvesting.youtube.internal;


import com.google.api.services.youtube.model.VideoStatistics;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;

import java.math.BigInteger;
import java.sql.Date;
import java.time.Duration;

public class VideoMapper {
    private VideoMapper() {
        throw new UnsupportedOperationException("Pure static class!");
    }

    public static VideoStatistic map(VideoStatistics statistics) {
        return new VideoStatistic(
                getLongOrZero(statistics.getViewCount()),
                getLongOrZero(statistics.getLikeCount()),
                getLongOrZero(statistics.getDislikeCount()));
    }

    private static long getLongOrZero(BigInteger input) {
        if (input != null) {
            return input.longValue();
        } else {
            return 0L;
        }
    }

    public static Video map(Source source, final com.google.api.services.youtube.model.Video input) {
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
