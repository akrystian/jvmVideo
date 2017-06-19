package pro.adamski.jvmvideo.classes;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionOnClassCreation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<ViewHelper> constructor = (Constructor<ViewHelper>) ViewHelper.class
                .getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}