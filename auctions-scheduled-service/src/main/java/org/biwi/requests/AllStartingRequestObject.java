package org.biwi.requests;

import javax.json.bind.annotation.JsonbProperty;

public class AllStartingRequestObject {

    @JsonbProperty("pageSize")
    private int pageSize;

    @JsonbProperty("page")
    private int page;

    @JsonbProperty(value = "filters", nillable = true)
    private Filter filter;

    @JsonbProperty(value = "sortBy", nillable = true)
    private String sortBy;

    public AllStartingRequestObject() {
        this.page = 0;
        this.pageSize = 20;
        this.filter = null;
        this.sortBy = "beginDate";
    }

    public AllStartingRequestObject(int pageSize, int page) {
        this.pageSize = pageSize;
        this.page = page;
        this.filter = null;
        this.sortBy = "beginDate";
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
