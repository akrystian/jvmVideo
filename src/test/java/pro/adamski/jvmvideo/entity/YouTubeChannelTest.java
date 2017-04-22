package pro.adamski.jvmvideo.entity;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class YouTubeChannelTest {
    YouTubeChannel instanceA =  new YouTubeChannel("name", new DateTime(DateTime.now()),
            "identifier");
    YouTubeChannel instanceB =  new YouTubeChannel("name", new DateTime(DateTime.now().minusMonths(1)),
            "identifier");
    YouTubeChannel instanceC =  new YouTubeChannel("names", new DateTime(DateTime.now()),
            "identifiers");
    @Test
    public void shouldProperlyEqualsTheSameChannels() {
        //then
        assertThat(instanceA.equals(instanceB),is(true));
    }

}