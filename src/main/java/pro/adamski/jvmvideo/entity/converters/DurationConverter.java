package pro.adamski.jvmvideo.entity.converters;

import javax.persistence.AttributeConverter;
import java.time.Duration;

/**
 * Converts duration into a number of seconds.
 *
 * @author akrystian.
 */
public class DurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long durationInSeconds) {
        return Duration.ofSeconds(durationInSeconds);
    }
}
