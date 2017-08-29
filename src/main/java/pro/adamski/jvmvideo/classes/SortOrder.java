package pro.adamski.jvmvideo.classes;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static pro.adamski.jvmvideo.classes.SortFactory.createSort;

/**
 * @author akrystian.
 */
public enum SortOrder {

    DATE_DESC(createSort(DESC, "publishDate")),
    VIEWS_DESC(createSort(DESC, "statistic_views"));

    private Sort sort;

    @SuppressWarnings("squid:UnusedPrivateMethod")
    SortOrder(Sort sort) {
        this.sort = sort;
    }

    public Sort applySort() {
        return sort;
    }
}

class SortFactory {
    private SortFactory() {
        throw new UnsupportedOperationException("Pure static function");
    }

    static Sort createSort(Sort.Direction direction, String property) {
        return new Sort(new Order(direction, property));
    }

}




