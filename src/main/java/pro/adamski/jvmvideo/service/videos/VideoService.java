package pro.adamski.jvmvideo.service.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.classes.SortOrder;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.VideoRepository;

import java.util.List;

/**
 * @author akrystian.
 */
@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> getVideosPage(final int page, final int pageSize, final SortOrder sortOrder) {
        return videoRepository.findAll(
                new PageRequest(page, pageSize,
                        sortOrder.applySort()
                ))
                .getContent();
    }

    public long getVideosSize() {
        return videoRepository.count();
    }
}
