package org.biwi.models;

import javax.json.bind.annotation.JsonbProperty;

public class StartingSoonRequestObject {
    /**
     * Number of hours
     */
    @JsonbProperty("range")
    private int range;

    /**
     * Max number of results
     */
    @JsonbProperty("limit")
    private int limit;

    public StartingSoonRequestObject() {
        this.range = 1;
        this.limit = 10;
    }

    public StartingSoonRequestObject(int range, int limit) {
        this.range = range;
        this.limit = limit;
    }

    public int getRange() {
        return range;
    }

    public int getLimit() {
        return limit;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
