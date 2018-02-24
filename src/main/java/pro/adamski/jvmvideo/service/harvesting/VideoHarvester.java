package pro.adamski.jvmvideo.service.harvesting;

import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.util.List;

/**
 * @author akrystian.
 */
class VideoHarvester {
    private final VideosFetcher videosFetcher;

    VideoHarvester(VideosFetcher videosFetcher) {
        this.videosFetcher = videosFetcher;
    }

    Video harvest(YouTubeChannel channel, String videoId) throws IOException {
        List<com.google.api.services.youtube.model.Video> videos = videosFetcher.fetch(videoId);
        return new VideoMapper(channel).map(videos.get(0));
    }
}

class VideoMapper {
    private final Source source;

    VideoMapper(Source source) {
        this.source = source;
    }

    Video map(final com.google.api.services.youtube.model.Video input) {
        return new Video(input.getId(),
                input.getSnippet().getTitle(),
                input.getSnippet().getDescription(),
                new Date(input.getSnippet().getPublishedAt().getValue()),
                Duration.parse(input.getContentDetails().getDuration()).getSeconds(),
                input.getSnippet().getThumbnails().getDefault().getUrl(),
                source);
    }
}