package pro.adamski.jvmvideo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.adamski.jvmvideo.entity.Video;
import pro.adamski.jvmvideo.repository.VideoRepository;
import pro.adamski.jvmvideo.service.videos.VideoService;

import java.sql.Date;
import java.time.Duration;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author akrystian
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VideoController.class)
public class VideoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoRepository videoRepository;

    @MockBean
    private VideoService videoService;

    @Before
    public void init() {
        given(videoRepository.findAll()).willReturn(Arrays.asList(
                new Video("id1", "title", "description", new Date(0L),
                        Duration.ofMinutes(55), "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
                        null, null),
                new Video("id1", "title2", "description2", new Date(0L),
                        Duration.ofMinutes(552), "https://i.ytimg.com/vi/zQll41ha5_g/default.jpg",
                        null, null))
        );
    }

    @Test
    public void shouldReturnListOfVideos() throws Exception {
        //then
        mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(header().string("Cache-Control", "max-age=7200, public"));
    }

    @Test
    public void shouldReturnListOfVideosWithCache() throws Exception {
        //then
        mvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnListOfVideosWithPagination() throws Exception {
        //then
        mvc.perform(get("/").param("size", "1").param("start", "1").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnListOfVideosWithPaginationAndSorting() throws Exception {
        //then
        mvc.perform(get("/").param("size", "1").param("start", "1").param("sort", "views_desc").accept
                (MediaType
                        .TEXT_PLAIN))
                .andExpect(status().isOk());
    }


}