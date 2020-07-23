package pro.adamski.jvmvideo.entity;

import org.joda.time.DateTime;

import javax.persistence.Entity;

/**
 * @author akrystian.
 */
@Entity
@SuppressWarnings("squid:S2160")
public class YouTubeChannel extends Source {
    private static final String YOUTUBE_LINK_PREFIX = "https://www.youtube.com/watch?v=";
    private String channelLink;

    public YouTubeChannel() {
        //hibernate entity
    }

    public YouTubeChannel(String name, DateTime lastHarvested, String channelLink) {
        super(name, lastHarvested, YOUTUBE_LINK_PREFIX);
        this.channelLink = channelLink;
    }

    @Override
    public String getSourceLink() {
        return channelLink;
    }
}
