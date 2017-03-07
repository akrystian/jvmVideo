package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Duration;


/**
 * @author akrystian
 */
@Entity
public class Video{
    @Id
    private String id;
    private String title;
    private String description;
    @NotNull
    private Date publishDate;
    private Duration duration;
    private String thumbnailLink;

    public Video(){
    }

    public Video(String id, String title, String description, Date publishDate, Duration duration, String
            thumbnailLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.duration = duration;
        this.thumbnailLink = thumbnailLink;
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Video)) return false;
        Video video = (Video) o;
        return Objects.equal(id, video.id) &&
                Objects.equal(title, video.title) &&
                Objects.equal(description, video.description) &&
                Objects.equal(publishDate, video.publishDate) &&
                Objects.equal(duration, video.duration) &&
                Objects.equal(thumbnailLink, video.thumbnailLink);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id, title, description, publishDate, duration, thumbnailLink);
    }
}
