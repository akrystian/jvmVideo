package pro.adamski.jvmvideo.repository.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pro.adamski.jvmvideo.entity.Video;

/**
 * @author krystian
 */
public interface SearchVideoRepository extends ElasticsearchRepository<Video, Long> {
    Video index(Video video);
}



