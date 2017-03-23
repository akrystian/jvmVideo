package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.adamski.jvmvideo.repository.VideoRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akrystian
 */
@Controller
public class VideoController {

    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_PAGE = 0;
    private static final String YOUTUBE_LINK_PREFIX = "https://www.youtube.com/watch?v=";
    private static final String P_PAGE_SIZE = "size";
    private static final String P_PAGE_START = "start";
    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @RequestMapping("/")
    public String latestVideos(Model model, HttpServletRequest request) {
        int page = getPage(request);
        int pageSize = getPageSize(request);
        model.addAttribute("videos", videoRepository.findAllByOrderByPublishDateDesc(new PageRequest(page, pageSize)));
        model.addAttribute("youtubeLinkPrefix", YOUTUBE_LINK_PREFIX);
        model.addAttribute("paginationItems", videoRepository.count());
        model.addAttribute("paginationPageSize", DEFAULT_PAGE_SIZE);
        model.addAttribute("paginationCurrent", page + 1);
        return "main";
    }

    private int getPageSize(HttpServletRequest request) {
        return (request.getParameter(P_PAGE_SIZE) != null)
                ? Integer.parseInt(request.getParameter(P_PAGE_SIZE))
                : DEFAULT_PAGE_SIZE;
    }

    private int getPage(HttpServletRequest request) {
        return (request.getParameter(P_PAGE_START) != null)
                ? Integer.parseInt(request.getParameter(P_PAGE_START)) - 1
                : DEFAULT_PAGE;
    }
}
