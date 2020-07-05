package org.biwi.models;

import javax.json.bind.annotation.JsonbProperty;

public class StartingSoonRequestObject {
    /**
     * Number of hours considered soon
     */
    @JsonbProperty("range")
    private int range;

    /**
     * Number of results to return per page
     */
    @JsonbProperty("pageSize")
    private int pageSize;

    /**
     * Which page to return
     */
    @JsonbProperty("page")
    private int page;

    public StartingSoonRequestObject() {
        this.range = 1;
        this.page = 1;
        this.pageSize = 10;
    }

    public StartingSoonRequestObject(int range, int pageSize, int page) {
        this.range = range;
        this.pageSize = pageSize;
        this.page = page;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
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
}
