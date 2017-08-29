package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.adamski.jvmvideo.classes.SortOrder;
import pro.adamski.jvmvideo.service.videos.VideoService;

import javax.servlet.http.HttpServletRequest;

import static pro.adamski.jvmvideo.classes.SortOrder.DATE_DESC;
import static pro.adamski.jvmvideo.classes.SortOrder.valueOf;

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
    private static final String P_SORT_ORDER = "sort";
    private static final SortOrder DEFAULT_SORT_ORDER = DATE_DESC;
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping("/")
    public String latestVideos(Model model, HttpServletRequest request) {
        final int page = getPage(request);
        final int pageSize = getPageSize(request);
        final SortOrder sortOrder = getSortOrder(request);
        model.addAttribute("videos", videoService.getVideosPage(page, pageSize, sortOrder));
        model.addAttribute("youtubeLinkPrefix", YOUTUBE_LINK_PREFIX);
        model.addAttribute("paginationItems", videoService.getVideosSize());
        model.addAttribute("paginationPageSize", DEFAULT_PAGE_SIZE);
        model.addAttribute("paginationCurrent", page + 1);
        model.addAttribute("sortOrder", sortOrder);
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

    private SortOrder getSortOrder(HttpServletRequest request) {
        return (request.getParameter(P_SORT_ORDER) != null)
                ? valueOf(request.getParameter(P_SORT_ORDER).toUpperCase())
                : DEFAULT_SORT_ORDER;
    }
}
