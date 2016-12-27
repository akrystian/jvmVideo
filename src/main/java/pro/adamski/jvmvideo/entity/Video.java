package pro.adamski.jvmvideo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

/**
 * @author akrystian
 */
@Entity
public class Video{
    @Id @GeneratedValue
    private long id;
    private String title;
    private String description;
    private Date publishDate;
    private Duration duration;
    private URL link;

    public Video(){
    }

    public Video(String title, String description, Date publishDate, Duration duration, URL link){
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.duration = duration;
        this.link = link;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Date getPublishDate(){
        return publishDate;
    }

    public Duration getDuration(){
        return duration;
    }

    public URL getLink(){
        return link;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Video)) return false;

        Video video = (Video) o;

        if (id != video.id) return false;
        if (title != null ? !title.equals(video.title) : video.title != null) return false;
        if (description != null ? !description.equals(video.description) : video.description != null) return false;
        if (publishDate != null ? !publishDate.equals(video.publishDate) : video.publishDate != null) return false;
        if (duration != null ? !duration.equals(video.duration) : video.duration != null) return false;
        return !(link != null ? !link.equals(video.link) : video.link != null);

    }

    @Override
    public int hashCode(){
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
