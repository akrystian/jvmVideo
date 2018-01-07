package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author akrystian.
 */
@SolrDocument
public class VideoDocument {

    @Id
    private String videoId;

    @Indexed
    @Field
    private String videoTitle;

    @Indexed
    @Field
    private String videoDescription;

    public VideoDocument(String videoId, String videoTitle, String videoDescription) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoDocument)) {
            return false;
        }
        VideoDocument that = (VideoDocument) o;
        return Objects.equal(videoId, that.videoId) &&
                Objects.equal(videoTitle, that.videoTitle) &&
                Objects.equal(videoDescription, that.videoDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(videoId, videoTitle, videoDescription);
    }

}
