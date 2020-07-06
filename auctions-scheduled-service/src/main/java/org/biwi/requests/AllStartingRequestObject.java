package org.biwi.requests;

import javax.json.bind.annotation.JsonbProperty;

public class AllStartingRequestObject {

    @JsonbProperty("pageSize")
    private int pageSize;

    @JsonbProperty("page")
    private int page;

    public AllStartingRequestObject() {
        this.page = 1;
        this.pageSize = 10;
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
}
