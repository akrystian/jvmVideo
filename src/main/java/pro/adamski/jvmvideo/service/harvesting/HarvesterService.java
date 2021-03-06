package pro.adamski.jvmvideo.service.harvesting;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.exceptions.HarvestingException;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.YouTubeChannel;
import pro.adamski.jvmvideo.repository.SourceRepository;
import pro.adamski.jvmvideo.repository.VideoRepository;
import pro.adamski.jvmvideo.service.harvesting.youtube.YouTubeService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author akrystian.
 */
@Service
public class HarvesterService {
    private static final Logger log = LoggerFactory.getLogger(HarvesterService.class);
    private final YouTubeService youTubeService;
    private final SourceRepository sourceRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public HarvesterService(YouTubeService youTubeService,
                            SourceRepository sourceRepository,
                            VideoRepository videoRepository) {
        this.youTubeService = youTubeService;
        this.sourceRepository = sourceRepository;
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init() {
        if (sourceRepository.findAll().isEmpty()) {
            List<YouTubeChannel> youTubeChannels = Collections.singletonList(
                    new YouTubeChannel("vJUG", new DateTime(0L),
                            "UCBxVkwrVRo8BnQ1g96MHZ0Q"));
            youTubeChannels.forEach(sourceRepository::save);
        }
        updateStats();
        harvestAllSources();
    }


    @Scheduled(cron = "0 0 3 * * MON")
    public void harvestAllSources() {
        sourceRepository.findAll().forEach(this::harvestSource);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void updateStats() {
        videoRepository.findAll().forEach(this::updateSingleVideoStats);
    }

    @Transactional
    private void updateSingleVideoStats(Video video) {
        try {
            videoRepository.save(youTubeService.updateStats(video));
        } catch (Exception e) {
            log.error("Problem with update stats for videoId: " + video.getVideoId() +  "videoLink"+ video
                    .getVideoLink() , e);
        }
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
            List<String> videoIds = youTubeService.harvestIdsFromChannel(channel, harvestingTime);
            for (String videoId : videoIds) {
                harvestSingleVideo(channel, videoId);
                harvestedVideosCounter++;
            }
        } catch (Exception e) {
            log.error("Problem with harvesting: ", e);
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
        videoRepository.save(youTubeService.harvestVideoFromChannel(channel, videoId));
    }

}
