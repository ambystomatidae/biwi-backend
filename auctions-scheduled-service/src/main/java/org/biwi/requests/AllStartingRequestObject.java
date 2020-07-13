package org.biwi.requests;

import javax.json.bind.annotation.JsonbProperty;

public class AllStartingRequestObject {

    @JsonbProperty("pageSize")
    private int pageSize;

    @JsonbProperty("page")
    private int page;

    @JsonbProperty(value = "filters", nillable = true)
    private Filter filter;

    public AllStartingRequestObject() {
        this.page = 0;
        this.pageSize = 20;
        this.filter = null;
    }

    public AllStartingRequestObject(int pageSize, int page) {
        this.pageSize = pageSize;
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
