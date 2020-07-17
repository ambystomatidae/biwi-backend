package org.biwi.repositories;

import org.biwi.models.ScheduledAuction;

import java.util.List;

public class ScheduledAuctionResponse {
    private List<ScheduledAuction> scheduled;
    private int numberOfPages;
    private long total;

    public ScheduledAuctionResponse(List<ScheduledAuction> scheduled, int numberOfPages, long total) {
        this.scheduled = scheduled;
        this.numberOfPages = numberOfPages;
        this.total = total;
    }

    public List<ScheduledAuction> getScheduled() {
        return scheduled;
    }

    public void setScheduled(List<ScheduledAuction> scheduled) {
        this.scheduled = scheduled;
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
