package pro.adamski.jvmvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.service.VideoRepository;

import java.util.List;

/**
 * @author akrystian
 */
@Controller
public class VideoController{

    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    @RequestMapping("/latest")
    public String latestVideos(Model model){
        List<Video> all = videoRepository.findAll();
        model.addAttribute("videos", all);
        return "latestVideos";
    }
}
