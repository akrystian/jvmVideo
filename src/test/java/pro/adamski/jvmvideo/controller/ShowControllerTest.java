package pro.adamski.jvmvideo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.adamski.jvmvideo.service.videos.VideoService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author akrystian.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ShowController.class)
public class ShowControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoService videoService;

    @Before
    public void init() {
        given(videoService.getVideoLink("id1")).willReturn("http://example.com");
    }

    @Test
    public void shouldReturnListOfVideos() throws Exception {
        //then
        mvc.perform(get("/show/id1"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("http://example.com*"));
    }


}