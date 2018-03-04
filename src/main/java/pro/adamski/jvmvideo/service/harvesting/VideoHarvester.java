package pro.adamski.jvmvideo.service.harvesting;

import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
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

