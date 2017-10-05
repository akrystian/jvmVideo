package pro.adamski.jvmvideo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import pro.adamski.jvmvideo.entity.Video;

import java.util.List;

/**
 * @author akrystian.
 */
public interface VideoRepository extends PagingAndSortingRepository<Video, String> {
    @Override
    Page<Video> findAll(Pageable pageable);

    @Override
    Video findOne(String videoId);

    @Override
    long count();

    @Override
    List<Video> findAll();

    @Override
    Video save(Video video);
}
