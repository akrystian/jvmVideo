package pro.adamski.jvmvideo.classes;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author akrystian.
 */
public class SortFactoryTest {
    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionOnClassCreation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SortFactory> constructor = SortFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}