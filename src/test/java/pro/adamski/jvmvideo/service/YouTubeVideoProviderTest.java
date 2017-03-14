package pro.adamski.jvmvideo.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.junit.Test;

/**
 * @author akrystian.
 */
public class YouTubeVideoProviderTest {
    private YouTubeVideoProvider instance = new YouTubeVideoProvider(null, "application");

    @Test(expected = GoogleJsonResponseException.class)
    public void shouldThrowExceptionOnMissingApiKey() throws Exception {
        //given
        instance.setUp();

        //then
        instance.fetchVideosFromChannel("videoChannel");
    }

}