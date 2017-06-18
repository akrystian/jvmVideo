package pro.adamski.jvmvideo.classes;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

/**
 * @author akrystian.
 */
public class ViewHelper {
    private ViewHelper() {
        throw new UnsupportedOperationException("Pure static class!");
    }

    public static String convertDuration(Duration input){
        return DurationFormatUtils.formatDuration(input.toMillis(), "H:mm:ss", true);
    }
}

