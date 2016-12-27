package pro.adamski.jvmvideo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.adamski.jvmvideo.entity.Video;

import java.util.Collection;

/**
 * @author akrystian
 */
public interface VideoRepository extends JpaRepository<Video,Long>{
    Collection<Video> findByTitle(String title);
}
