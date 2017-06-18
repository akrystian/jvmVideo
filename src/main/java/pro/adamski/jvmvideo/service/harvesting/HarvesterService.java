package pro.adamski.jvmvideo.service.harvesting;

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
public class HarvesterService {
    private static final Logger log = LoggerFactory.getLogger(HarvesterService.class);
    private final YoutubeHarvester youtubeHarvester;
    private final SourceRepository sourceRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public HarvesterService(YoutubeHarvester youtubeHarvester,
                            SourceRepository sourceRepository,
                            VideoRepository videoRepository) {
        this.youtubeHarvester = youtubeHarvester;
        this.sourceRepository = sourceRepository;
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init() {
        if (sourceRepository.findAll().isEmpty()) {
            List<YouTubeChannel> youTubeChannels = Arrays.asList(
                    new YouTubeChannel("Toronto JUG", new DateTime(0L),
                            "UC6D58UvAH98IaMVZr80-03g"),
                    new YouTubeChannel("Warsaw JUG", new DateTime(0L),
                            "UC2coGyxf5x_CzJ3l4F-N-Sw"));
            youTubeChannels.forEach(sourceRepository::save);
        }
        harvestAllSources();
    }


    @Scheduled(cron = "0 0 * * * MON")
    public void harvestAllSources() {
        sourceRepository.findAll().forEach(this::harvestSource);
    }

    @Transactional
    private void harvestSource(final Source source) {
        final YouTubeChannel channel = (YouTubeChannel) source;
        final long now = System.currentTimeMillis();
        Collection<Video> videos = youtubeHarvester.harvest(channel, now);
        log.info("Harvested {} from {} channel.", videos.size(), channel.getName());
        videos.forEach(videoRepository::save);
        channel.setLastHarvested(new DateTime(now));
        sourceRepository.save(channel);
    }

}
