package pro.adamski.jvmvideo.classes;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static pro.adamski.jvmvideo.classes.SortFactory.createSort;

/**
 * @author akrystian.
 */
public enum SortOrder {

    DATE_DESC(DESC, "publishDate", "date"),
    VIEWS_DESC(DESC, "statistic_views", "views"),
    LIKES_DESC(DESC, "statistic_liked", "likes"),
    LIKES_RATIO_DESC(DESC, "statistic_likesRatio", "likes ratio");

    public static final SortOrder DEFAULT_SORT_ORDER = DATE_DESC;
    private Sort sort;
    private String description;

    @SuppressWarnings("squid:UnusedPrivateMethod")
    SortOrder(Sort.Direction direction, String property, String description) {
        this.sort = createSort(direction, property);
        this.description = description;
    }

    public Sort applySort() {
        return sort;
    }

    public String getDescription() {
        return description;
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




