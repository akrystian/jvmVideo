package pro.adamski.jvmvideo.service.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.jpa.VideoRepository;
import pro.adamski.jvmvideo.repository.search.SearchVideoRepository;

import java.util.List;

/**
 * @author akrystian.
 */
@Service
public class VideoService {

    private final VideoRepository videoRepository;

    private final SearchVideoRepository searchVideoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, SearchVideoRepository searchVideoRepository) {
        this.videoRepository = videoRepository;
        this.searchVideoRepository = searchVideoRepository;
    }

    public List<Video> getVideosPage(final int page, final int pageSize) {
        //return videoRepository.findAllByOrderByPublishDateDesc(new PageRequest(page, pageSize)).getContent();

        return searchVideoRepository.findAllByTitle("java",new PageRequest(page, pageSize)).getContent();
    }

    public long getVideosSize() {
        return videoRepository.count();
    }
}
