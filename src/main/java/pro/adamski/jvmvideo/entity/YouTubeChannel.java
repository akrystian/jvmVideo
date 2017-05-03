package pro.adamski.jvmvideo.entity;

import org.joda.time.DateTime;

import javax.persistence.Entity;

/**
 * @author akrystian.
 */
@Entity
public class YouTubeChannel extends Source {
    private String channelLink;

    public YouTubeChannel() {
        //hibernate entity
    }

    public YouTubeChannel(String name, DateTime lastHarvested, String channelLink) {
        super(name, lastHarvested);
        this.channelLink = channelLink;
    }

    @Override
    public String getSourceLink() {
        return channelLink;
    }
}
