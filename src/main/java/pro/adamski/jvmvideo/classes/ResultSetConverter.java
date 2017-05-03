package pro.adamski.jvmvideo.classes;

import com.google.api.services.youtube.model.SearchResult;
import pro.adamski.jvmvideo.entity.Source;
import pro.adamski.jvmvideo.entity.Video;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akrystian
 */
public class ResultSetConverter {
    static final String VIDEO_KIND = "youtube#video";
    private final Source source;

    public ResultSetConverter(Source source) {
        this.source = source;
    }

    public List<Video> convertVideos(final List<SearchResult> searchResultList) {
        return searchResultList.stream()
                .filter(searchResult -> searchResult != null
                        && searchResult.getId().getKind() != null
                        && searchResult.getId().getKind().equals(VIDEO_KIND)
                )
                .map(this::mapToVideo)
                .collect(Collectors.toList());
    }

    private Video mapToVideo(final SearchResult singleVideo) {
        return new Video(singleVideo.getId().getVideoId(),
                singleVideo.getSnippet().getTitle(),
                singleVideo.getSnippet().getDescription(),
                new Date(singleVideo.getSnippet().getPublishedAt().getValue()),
                null,
                singleVideo.getSnippet().getThumbnails().getDefault().getUrl(),
                source);
    }
}