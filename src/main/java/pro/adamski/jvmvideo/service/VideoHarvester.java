package pro.adamski.jvmvideo.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;
import pro.adamski.jvmvideo.repository.VideoRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author akrystian.
 */
@Service
public class VideoHarvester {
    public static final Logger log = LoggerFactory.getLogger(VideoHarvester.class);
    private final YouTubeVideoProvider youTubeVideoProvider;
    private final SourceRepository sourceRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public VideoHarvester(YouTubeVideoProvider youTubeVideoProvider,
                          SourceRepository sourceRepository,
                          VideoRepository videoRepository) {
        this.youTubeVideoProvider = youTubeVideoProvider;
        this.sourceRepository = sourceRepository;
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init() {
        if (videoRepository.findAll().isEmpty()) {
            List<YouTubeChannel> youTubeChannels = Arrays.asList(
                    new YouTubeChannel("Toronto JUG", new DateTime(DateTime.now()),
                            "UC6D58UvAH98IaMVZr80-03g"),
                    new YouTubeChannel("Warsaw JUG", new DateTime(DateTime.now()),
                            "UC2coGyxf5x_CzJ3l4F-N-Sw"));
            sourceRepository.save(youTubeChannels);
        }
        harvestAllSources();
    }


    @Scheduled(cron = "0 0 * * * MON")
    public void harvestAllSources() {
        sourceRepository.findAll().forEach(this::harvestAllSources);
    }

    @Transactional
    private void harvestAllSources(Source source) {
        final YouTubeChannel channel = (YouTubeChannel) source;
        Collection<Video> videos = youTubeVideoProvider.fetchVideos(channel);
        log.info("Harvested {} from {} channel.", videos.size(), channel.getName());
        videos.forEach(videoRepository::save);
    }

}
