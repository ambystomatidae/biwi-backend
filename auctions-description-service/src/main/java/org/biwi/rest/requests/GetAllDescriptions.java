package org.biwi.rest.requests;

import javax.json.bind.annotation.JsonbProperty;

public class GetAllDescriptions {
    @JsonbProperty("pageSize")
    private int pageSize;
    @JsonbProperty("page")
    private int page;

    public GetAllDescriptions() {
        this.pageSize = 20;
        this.page = 1;
    }

    public GetAllDescriptions(int pageSize, int page) {
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
