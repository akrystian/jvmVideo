package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.persistence.Entity;

/**
 * @author akrystian.
 */
@Entity
public class YouTubeChannel extends Source {
    private String channelLink;

    public YouTubeChannel(){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YouTubeChannel)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        YouTubeChannel that = (YouTubeChannel) o;
        return Objects.equal(channelLink, that.channelLink);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), channelLink);
    }
}
