package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author akrystian.
 */
public class YouTubeUtilsTest {
    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionOnClassCreation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<YouTubeUtils> constructor = YouTubeUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}