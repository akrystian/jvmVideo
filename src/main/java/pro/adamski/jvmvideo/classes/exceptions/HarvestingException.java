package pro.adamski.jvmvideo.classes.exceptions;

/**
 * @author akrystian.
 */
public class HarvestingException extends RuntimeException {
    public HarvestingException(Exception e) {
        super(e);
    }

    public HarvestingException(String message) {
        super(message);
    }
}
