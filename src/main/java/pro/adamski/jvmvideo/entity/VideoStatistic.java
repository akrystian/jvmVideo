package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;

/**
 * @author akrystian.
 */
public class VideoStatistic {

    private long views;
    private long liked;
    private long disliked;
    private double likesRatio;

    public VideoStatistic(long views, long liked, long disliked) {
        this.views = views;
        this.liked = liked;
        this.disliked = disliked;
        this.likesRatio = likesRatio(views, liked);
    }

    private double likesRatio(long views, long liked) {
        if (views < 100 || liked < 10) {
            return 0.0D;
        }
        return ((double) liked / views) * 100.0D;
    }

    public void setAll(VideoStatistic all) {
        this.views = all.getViews();
        this.liked = all.getLiked();
        this.disliked = all.getDisliked();
        this.likesRatio = likesRatio(views, liked);
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

    public double getLikesRatio() {
        return likesRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoStatistic)) return false;
        VideoStatistic statistic = (VideoStatistic) o;
        return views == statistic.views &&
                liked == statistic.liked &&
                disliked == statistic.disliked &&
                Double.compare(statistic.likesRatio, likesRatio) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(views, liked, disliked, likesRatio);
    }
}
