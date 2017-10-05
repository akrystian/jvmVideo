package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.adamski.jvmvideo.service.videos.VideoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author akrystian.
 */
@RestController
public class ShowController {
    private VideoService videoService;

    @Autowired
    public ShowController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping(value = "/show/{id}", method = GET)
    public void showVideo(@PathVariable String id, HttpServletResponse response) throws IOException {
        final String videoLink = videoService.getVideoLink(id);
        response.sendRedirect(videoLink);
    }

}
