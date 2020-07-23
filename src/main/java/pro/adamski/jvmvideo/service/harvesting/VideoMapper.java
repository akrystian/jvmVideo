package pro.adamski.jvmvideo.service.harvesting;

import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.entity.VideoDocument;

import java.sql.Date;
import java.time.Duration;

public class VideoMapper {
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

    VideoDocument map (final Video video){
        return new VideoDocument(
                video.getVideoId(),
                video.getTitle(),
                video.getDescription(),
                video.getPublishDate(),
                video.getLength(),
                video.getThumbnailLink(),
                video.getSource().getName(),
                source.getVideoLinkPrefix() + video.getVideoId());
    }
}
