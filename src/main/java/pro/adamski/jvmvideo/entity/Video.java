package pro.adamski.jvmvideo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author akrystian
 */
@Entity
@Document(indexName = "video")
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnore
    private Source source;


    public Video() {
        //hibernate entity
        publishDate = new Date(0L);
    }

    @SuppressWarnings("squid:S2637")
    @JsonCreator
    public Video(@JsonProperty("videoId") String videoId,
                 @JsonProperty("title") String title,
                 @JsonProperty("description") String description,
                 @JsonProperty("publishDate") Date publishDate,
                 @JsonProperty("length") long length,
                 @JsonProperty("thumbnailLink") String thumbnailLink
                 ) {
        this(videoId,title,description,publishDate,length,thumbnailLink,null);
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
