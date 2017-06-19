package pro.adamski.jvmvideo.service.harvesting;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;
import pro.adamski.jvmvideo.repository.VideoRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
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

    private void harvestSource(final Source source) {
        final YouTubeChannel channel = (YouTubeChannel) source;
        final long now = System.currentTimeMillis();
        final int harvestedRecordsNumber = harvestNewRecordsFromChannel(channel, now);
        log.info("Harvested {} from {} channel.", harvestedRecordsNumber, channel.getName());
        updateLastHarvested(channel, now);
    }

    private int harvestNewRecordsFromChannel(YouTubeChannel channel, long harvestingTime) {
        int harvestedVideosCounter = 0;
        try {
            List<String> videoIds = youtubeHarvester.harvestIdentifiers(channel, harvestingTime);
            for (String videoId : videoIds) {
                harvestSingleVideo(channel, videoId);
                harvestedVideosCounter++;
            }
        } catch (IOException e) {
            throw new HarvestingException(e);
        }
        return harvestedVideosCounter;
    }

    @Transactional
    private void updateLastHarvested(YouTubeChannel channel, long now) {
        channel.setLastHarvested(new DateTime(now));
        sourceRepository.save(channel);
    }

    @Transactional
    private void harvestSingleVideo(YouTubeChannel channel, String videoId) throws IOException {
        videoRepository.save(youtubeHarvester.harvestVideo(channel, videoId));
    }

}
