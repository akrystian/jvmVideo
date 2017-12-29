package pro.adamski.jvmvideo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.adamski.jvmvideo.entity.Source;

import java.util.List;

/**
 * @author akrystian
 */
public interface SourceRepository extends JpaRepository<Source, Long> {
    Source saveAndFlush(Source source);

    List<Source> findAll();
}