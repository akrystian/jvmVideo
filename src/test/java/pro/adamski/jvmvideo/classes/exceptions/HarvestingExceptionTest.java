package pro.adamski.jvmvideo.classes.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class HarvestingExceptionTest {
    @Test
    public void shouldCreateExceptionByMessage() {
        //given
        final String message = "Simple Message";

        //when
        final HarvestingException harvestingException = new HarvestingException(message);

        //then
        assertThat(harvestingException.getMessage(), is(message));
    }

    @Test
    public void shouldCreateExceptionByWrappedException() {
        //given
        final UnsupportedOperationException wrapped = new UnsupportedOperationException();

        //when
        final HarvestingException harvestingException = new HarvestingException(wrapped);

        //then
        assertThat(harvestingException.getCause(), is(wrapped));
    }
}