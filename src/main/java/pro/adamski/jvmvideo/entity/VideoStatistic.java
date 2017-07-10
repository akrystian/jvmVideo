package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author akrystian.
 */
@Entity
public class VideoStatistic {
    @Id
    @GeneratedValue
    private long id;

    private long views;
    private long liked;
    private long disliked;

    public VideoStatistic() {
        // hibernate entity
    }

    public VideoStatistic(long views, long liked, long disliked) {
        this.views = views;
        this.liked = liked;
        this.disliked = disliked;
    }

    public long getViews() {
        return views;
    }

    public long getLiked() {
        return liked;
    }

    public long getDisliked() {
        return disliked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoStatistic)) {
            return false;
        }
        VideoStatistic that = (VideoStatistic) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
