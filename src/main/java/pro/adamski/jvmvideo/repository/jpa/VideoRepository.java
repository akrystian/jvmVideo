package pro.adamski.jvmvideo.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import pro.adamski.jvmvideo.entity.Video;

import java.util.List;

/**
 * @author akrystian.
 */
public interface VideoRepository extends Repository<Video, Long> {
    Page<Video> findAllByOrderByPublishDateDesc(Pageable pageable);

    long count();

    List<Video> findAll();

    void save(Video video);
}
