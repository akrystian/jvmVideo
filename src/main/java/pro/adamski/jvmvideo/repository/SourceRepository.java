package pro.adamski.jvmvideo.repository;

import org.springframework.data.repository.Repository;
import pro.adamski.jvmvideo.entity.Source;

import java.util.List;

/**
 * @author akrystian
 */
public interface SourceRepository extends Repository<Source, Long> {
    void save(Source source);

    List<Source> findAll();
}