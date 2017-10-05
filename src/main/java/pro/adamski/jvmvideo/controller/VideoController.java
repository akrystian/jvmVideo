package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.adamski.jvmvideo.classes.Pagination;
import pro.adamski.jvmvideo.classes.SortOrder;
import pro.adamski.jvmvideo.service.videos.VideoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pro.adamski.jvmvideo.classes.CacheControlGenerator.generateHeader;
import static pro.adamski.jvmvideo.classes.SortOrder.DEFAULT_SORT_ORDER;
import static pro.adamski.jvmvideo.classes.SortOrder.valueOf;

/**
 * @author akrystian
 */
@Controller
public class VideoController {
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_PAGE = 0;

    private static final String P_PAGE_SIZE = "size";
    private static final String P_PAGE_START = "start";
    private static final String P_SORT_ORDER = "sort";

    private final VideoService videoService;
    private final int cachePeriod;

    @Autowired
    public VideoController(VideoService videoService,
                           @Value("${jvmvideo.resources.cache-period}") int cachePeriod) {
        this.videoService = videoService;
        this.cachePeriod = cachePeriod;
    }

    @RequestMapping("/")
    public String latestVideos(Model model, HttpServletRequest request, HttpServletResponse response) {
        final int page = getPage(request);
        final int pageSize = getPageSize(request);
        final SortOrder sortOrder = getSortOrder(request);
        final Pagination pagination = new Pagination(videoService.getVideosSize(), DEFAULT_PAGE_SIZE,
                page + 1, createOrderString(sortOrder, "start="));
        model.addAttribute("videos", videoService.getVideosPage(page, pageSize, sortOrder));
        model.addAttribute("pagination", pagination);
        model.addAttribute("sortOrder", sortOrder);
        response.addHeader("Cache-Control", generateHeader(cachePeriod));
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

    private String createOrderString(SortOrder sortOrder, String input) {
        if (DEFAULT_SORT_ORDER.equals(sortOrder)) {
            return "?" + input;
        } else {
            return "?" + P_SORT_ORDER + "=" + sortOrder.toString().toLowerCase() + "&" + input;
        }
    }
}


