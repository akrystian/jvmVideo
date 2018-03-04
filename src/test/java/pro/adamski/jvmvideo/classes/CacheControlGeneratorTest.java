package pro.adamski.jvmvideo.classes;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author akrystian.
 */
public class CacheControlGeneratorTest {
    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionOnClassCreation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<CacheControlGenerator> constructor = (Constructor<CacheControlGenerator>) CacheControlGenerator.class
                .getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}