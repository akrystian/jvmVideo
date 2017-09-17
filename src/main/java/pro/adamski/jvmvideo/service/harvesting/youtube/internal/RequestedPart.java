package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

/**
 * @author akrystian.
 */
public enum RequestedPart {

    ALL("id,snippet,statistics,contentDetails"), STATS("statistics");

    private final String partString;

    RequestedPart(String partString) {
        this.partString = partString;
    }

    String parts() {
        return this.partString;
    }
}
