package pro.adamski.jvmvideo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.service.VideoRepository;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author akrystian
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VideoController.class)
public class VideoControllerTest{

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoRepository videoRepository;

    @Test
    public void shouldReturnListOfVideos() throws Exception{
        //given

        given(videoRepository.findAll()).willReturn(Arrays.asList(
                new Video("title", "description", new Date(),
                        Duration.ofMinutes(55), new URL("https://www.youtube.com/watch?v=0drVTNFUqgc")),
                new Video("title2", "description2", new Date(),
                        Duration.ofMinutes(552), new URL("https://www.youtube.com/watch?v=0drVTNFUqgc"))
        ));
        //then
        mvc.perform(get("/latest").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

    }
}