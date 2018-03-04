package pro.adamski.jvmvideo.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pro.adamski.jvmvideo.entity.VideoDocument;

/**
 * @author krystian
 */
public interface SearchVideoRepository extends ElasticsearchRepository<VideoDocument, Long> {
    VideoDocument index(VideoDocument video);

    Page<VideoDocument> findAllByTitle(String title, Pageable pageable);
}



