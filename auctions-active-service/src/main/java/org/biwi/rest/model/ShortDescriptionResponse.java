package org.biwi.rest.model;

import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

public class ShortDescriptionResponse {
    @JsonbProperty(value = "auctions")
    private List<ShortDescription> shortDespcriptions;
    @JsonbProperty(value = "numberOfPages")
    private int numberOfPages;
    @JsonbProperty(value = "total")
    private long total;

    public ShortDescriptionResponse(List<ShortDescription> auctions, int numberOfPages, long total) {
        this.shortDespcriptions = auctions;
        this.numberOfPages = numberOfPages;
        this.total = total;
    }

    public ShortDescriptionResponse() {
        this.shortDespcriptions = new ArrayList<>();
    }

    public List<ShortDescription> getAuctions() {
        return shortDespcriptions;
    }

    public void setAuctions(List<ShortDescription> auctions) {
        this.shortDespcriptions = auctions;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}