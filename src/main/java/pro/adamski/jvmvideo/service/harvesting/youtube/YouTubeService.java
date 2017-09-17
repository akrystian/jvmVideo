package pro.adamski.jvmvideo.service.harvesting.youtube;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoStatistic;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart;
import pro.adamski.jvmvideo.service.harvesting.youtube.internal.VideoMapper;
import pro.adamski.jvmvideo.service.harvesting.youtube.internal.YouTubeProxy;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static pro.adamski.jvmvideo.service.harvesting.youtube.YouTubeService.VIDEO_KIND;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart.ALL;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart.STATS;

/**
 * @author akrystian.
 */
@Service
public class YouTubeService {
    public static final String VIDEO_KIND = "youtube#video";
    private final YouTubeProxy youTubeProxy;

    @Autowired
    public YouTubeService(YouTubeProxy youTubeProxy) {
        this.youTubeProxy = youTubeProxy;
    }

    public List<String> harvestIdsFromChannel(YouTubeChannel channel, long harvestingTime) throws IOException {
        final String sourceLink = channel.getSourceLink();
        final DateTime publishedBefore = new DateTime(harvestingTime);
        final DateTime publishedAfter = new DateTime(channel.getLastHarvested().toDate());
        return youTubeProxy.listIdsFromChannel(sourceLink, publishedAfter, publishedBefore)
                .stream()
                .filter(new VideoFilter())
                .map(SearchResult::getId)
                .map(ResourceId::getVideoId)
                .collect(toList());
    }

    public Video updateStats(Video video) throws IOException {
        final VideoStatistic old = video.getStatistic();
        old.setAll(harvestStats(video.getVideoId()));
        return video;
    }

    public Video harvestVideoFromChannel(YouTubeChannel channel, String videoId) throws IOException {
        final com.google.api.services.youtube.model.Video input = getVideo(videoId, ALL);
        return VideoMapper.map(channel, input);
    }

    private VideoStatistic harvestStats(String videoId) throws IOException {
        final com.google.api.services.youtube.model.Video input = getVideo(videoId, STATS);
        return VideoMapper.map(input.getStatistics());
    }

    private com.google.api.services.youtube.model.Video getVideo(String videoId, RequestedPart requestedPart) throws
            IOException {
        List<com.google.api.services.youtube.model.Video> videos = youTubeProxy.getVideo(videoId, requestedPart);
        if (videos.size() != 1) {
            throw new HarvestingException("Unsupported number of records! Number : " + videos.size() + "!");
        }
        return videos.get(0);
    }
}

class VideoFilter implements Predicate<SearchResult> {
    @Override
    public boolean test(SearchResult searchResult) {
        return searchResult != null
                && searchResult.getId() != null
                && searchResult.getId().getKind() != null
                && searchResult.getId().getKind().equals(VIDEO_KIND);
    }
}

