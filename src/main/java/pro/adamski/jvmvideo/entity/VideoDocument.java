package pro.adamski.jvmvideo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author akrystian
 */
@Entity
@Document(indexName = "video")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    private String videoId;
    private String videoUrl;
    private String title;
    private String description;
    private Date publishDate;
    private long length;
    private String thumbnailLink;
    private String source;

    @SuppressWarnings("squid:S2637")
    @JsonCreator
    public VideoDocument(@JsonProperty("video_id") String videoId,
                         @JsonProperty("title") String title,
                         @JsonProperty("description") String description,
                         @JsonProperty("publish_date") Date publishDate,
                         @JsonProperty("length") long length,
                         @JsonProperty("thumbnail_link") String thumbnailLink,
                         @JsonProperty("source") String source,
                         @JsonProperty("video_url") String videoUrl
                 ) {
        this.publishDate = notNull(publishDate);
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.length = length;
        this.thumbnailLink = thumbnailLink;
        this.source = source;
        this.videoUrl = videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public long getLength() {
        return length;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public String getSource() {
        return source;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoDocument)) {
            return false;
        }
        VideoDocument video = (VideoDocument) o;
        return videoId == video.videoId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(videoId);
    }
}
