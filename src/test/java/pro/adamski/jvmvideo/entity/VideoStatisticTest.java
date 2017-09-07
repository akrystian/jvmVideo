package pro.adamski.jvmvideo.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author akrystian.
 */
public class VideoStatisticTest {

    @Test
    public void shouldCompareEqualValues() {
        //given
        VideoStatistic instance = new VideoStatistic(1, 2, 3);
        VideoStatistic instance2 = new VideoStatistic(1, 2, 3);

        //then
        assertThat(instance, is(instance2));
    }

    @Test
    public void shouldCompareEqualHashCode() {
        //given
        VideoStatistic instance = new VideoStatistic(1, 2, 3);
        VideoStatistic instance2 = new VideoStatistic(1, 2, 3);

        //then
        assertThat(instance.hashCode(), is(instance2.hashCode()));
    }

    @Test
    public void shouldGetsProperValues() {
        //given
        VideoStatistic instance = new VideoStatistic(1, 2, 3);

        //then
        assertThat(instance.getViews(), is(1L));
        assertThat(instance.getLiked(), is(2L));
        assertThat(instance.getDisliked(), is(3L));
    }
}