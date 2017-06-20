package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

/**
 * @author akrystian.
 */
class IdentifiersFetcher {
    private static final String REQUESTED_PART = "id";
    private static final Long MAX_RESULTS = 50L;
    private final YouTube youTube;
    private final String apiKey;

    public IdentifiersFetcher(YouTube youTube, String apiKey) {
        this.youTube = youTube;
        this.apiKey = apiKey;
    }

    List<SearchResult> fetch(String sourceLink, DateTime publishedBefore, DateTime publishedAfter) throws IOException {
        YouTube.Search.List search = youTube.search().list(REQUESTED_PART);
        search.setKey(apiKey).setChannelId(sourceLink).setMaxResults(MAX_RESULTS)
                .setPublishedBefore(publishedBefore).setPublishedAfter(publishedAfter);
        return search.execute().getItems();
    }
}