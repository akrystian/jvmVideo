package pro.adamski.jvmvideo.classes;

public class Pagination {
    private final long allItems;
    private final int pageSize;
    private final int currentPage;
    private final String hrefPrefix;

    public Pagination(long allItems, int pageSize, int currentPage, String hrefPrefix) {
        this.allItems = allItems;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.hrefPrefix = hrefPrefix;
    }

    public long getAllItems() {
        return allItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getHrefPrefix() {
        return hrefPrefix;
    }
}