package pro.adamski.jvmvideo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.adamski.jvmvideo.entity.Source;

/**
 * @author akrystian
 */
public interface SourceRepository extends JpaRepository<Source, Long> {
}