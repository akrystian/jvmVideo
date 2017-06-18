package pro.adamski.jvmvideo.classes;

import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static pro.adamski.jvmvideo.classes.ViewHelper.convertDuration;

/**
 * @author akrystian.
 */
public class ViewHelperTest {

    @Test
    public void shouldConvertDuration() {
        //given
        final Duration duration = Duration.ofSeconds(55555);

        //when
        final String result = convertDuration(duration);

        //then
        assertThat(result,is("15:25:55"));
    }

}