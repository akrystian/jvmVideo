package pro.adamski.jvmvideo.repository.solr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import pro.adamski.jvmvideo.entity.VideoDocument;

/**
 * @author akrystian.
 */
public interface VideoDocumentsRepository extends SolrCrudRepository<VideoDocument, String> {
    @Override
    VideoDocument save(VideoDocument entity);

    Page<VideoDocument> findByVideoTitle(String videoTitle, Pageable page);
}
