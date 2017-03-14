package pro.adamski.jvmvideo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.adamski.jvmvideo.entity.Video;

/**
 * @author akrystian
 */
public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByOrderByPublishDateDesc(Pageable pageable);
}