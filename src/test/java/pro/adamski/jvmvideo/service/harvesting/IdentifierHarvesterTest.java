package pro.adamski.jvmvideo.service.harvesting;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import org.junit.Test;
import pro.adamski.jvmvideo.entity.YouTubeChannel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static pro.adamski.jvmvideo.service.harvesting.VideoFilter.VIDEO_KIND;

/**
 * @author akrystian.
 */
public class IdentifierHarvesterTest {

    @Test
    public void shouldHarvestIdentifiers() throws IOException {
        //given
        final String videoId = "videoId";

        final IdentifiersFetcher identifiersFetcher = mock(IdentifiersFetcher.class);
        final SearchResult searchResult = prepareSearchResult(videoId, VIDEO_KIND);
        final List<SearchResult> results = Collections.singletonList(searchResult);
        given(identifiersFetcher.fetch(anyString(), any(DateTime.class), any(DateTime.class))).willReturn(results);
        IdentifierHarvester harvester = new IdentifierHarvester(identifiersFetcher);
        final YouTubeChannel channel = new YouTubeChannel("name",
                new org.joda.time.DateTime(org.joda.time.DateTime.now()),
                "identifier");

        //when
        final List<String> harvest = harvester.harvest(channel, System.currentTimeMillis());

        //then
        assertThat(harvest, hasItem(videoId));
    }

    private SearchResult prepareSearchResult(String videoId, String videoKind) {
        SearchResult searchResult;
        searchResult = new SearchResult().setId(new ResourceId().setKind(videoKind)
                .setVideoId(videoId));
        return searchResult;
    }
}