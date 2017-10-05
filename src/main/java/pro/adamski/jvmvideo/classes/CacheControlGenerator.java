package pro.adamski.jvmvideo.classes;

import org.springframework.http.CacheControl;

import java.util.concurrent.TimeUnit;

/**
 * @author akrystian.
 */
public class CacheControlGenerator {
    private CacheControlGenerator() {
        throw new UnsupportedOperationException("Pure static class!");
    }

    public static CacheControl generate(int period) {
        return CacheControl.maxAge(period, TimeUnit.SECONDS).cachePublic();
    }

    public static String generateHeader(int period) {
        return generate(period).getHeaderValue();
    }
}
