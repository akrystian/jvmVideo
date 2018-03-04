package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author akrystian
 */
@Entity
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String videoId;
    private String title;
    private String description;
    @NotNull
    private Date publishDate;
    private long length;
    private String thumbnailLink;
    @ManyToOne
    private Source source;


    public Video() {
        //hibernate entity
        publishDate = new Date(0L);
    }

    @SuppressWarnings("squid:S2637")
    public Video(String videoId,
                 String title,
                 String description,
                 Date publishDate,
                 long length,
                 String thumbnailLink,
                 Source source) {
        this();
        this.publishDate = notNull(publishDate);
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.length = length;
        this.thumbnailLink = thumbnailLink;
        this.source = source;
    }

    public long getId() {
        return id;
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

    public Source getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Video)) {
            return false;
        }
        Video video = (Video) o;
        return id == video.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
