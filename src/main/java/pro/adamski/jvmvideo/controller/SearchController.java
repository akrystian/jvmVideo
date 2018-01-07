package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.adamski.jvmvideo.entity.VideoDocument;
import pro.adamski.jvmvideo.repository.solr.VideoDocumentsRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author akrystian.
 */
@RestController
public class SearchController {
    private VideoDocumentsRepository videoDocumentsRepository;

    @Autowired
    public SearchController(VideoDocumentsRepository videoDocumentsRepository) {
        this.videoDocumentsRepository = videoDocumentsRepository;
    }

    @RequestMapping(value = "/search/{query}", method = GET)
    public List<VideoDocument> showVideo(@PathVariable String query, HttpServletResponse response) throws
            IOException {
        return videoDocumentsRepository.findByVideoTitle(query, new PageRequest(0, 10)).getContent();
    }
}
