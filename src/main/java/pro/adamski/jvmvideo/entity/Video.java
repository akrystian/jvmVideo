package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Duration;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author akrystian
 */
@Entity
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    @NotNull
    private Date publishDate;
    private Duration duration;
    private String thumbnailLink;
    @ManyToOne
    private Source source;

    public Video() {
        //hibernate entity
        publishDate = new Date(0L);
    }

    @SuppressWarnings("squid:S2637")
    public Video(String id, String title, String description, Date publishDate, Duration duration,
                 String thumbnailLink, Source source) {
        this();
        this.publishDate = notNull(publishDate);
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.thumbnailLink = thumbnailLink;
        this.source = source;
    }

    public String getId() {
        return id;
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

    public Duration getDuration() {
        return duration;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public Source getSource() {
        return source;
    }

    @Override
    @SuppressWarnings("squid:S1067")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Video)) {
            return false;
        }
        Video video = (Video) o;
        return Objects.equal(id, video.id) &&
                Objects.equal(title, video.title) &&
                Objects.equal(description, video.description) &&
                Objects.equal(publishDate, video.publishDate) &&
                Objects.equal(duration, video.duration) &&
                Objects.equal(thumbnailLink, video.thumbnailLink) &&
                Objects.equal(source,video.source);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description, publishDate, duration, thumbnailLink, source);
    }
}
