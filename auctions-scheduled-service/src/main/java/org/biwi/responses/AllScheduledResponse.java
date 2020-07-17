package org.biwi.responses;

import org.biwi.external.ShortDescription;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class AllScheduledResponse {
    @JsonbProperty(value = "auctions")
    private List<ShortDescription> auctions;
    @JsonbProperty(value = "numberOfPages")
    private int numberOfPages;
    @JsonbProperty(value = "total")
    private long total;

    public AllScheduledResponse(List<ShortDescription> auctions, int numberOfPages, long total) {
        this.auctions = auctions;
        this.numberOfPages = numberOfPages;
        this.total = total;
    }

    public List<ShortDescription> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<ShortDescription> auctions) {
        this.auctions = auctions;
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

    public void setTotal(long total) {
        this.total = total;
    }
}
