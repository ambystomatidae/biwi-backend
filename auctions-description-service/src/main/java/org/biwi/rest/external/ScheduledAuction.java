package org.biwi.rest.external;
import org.biwi.rest.models.AuctionDescription;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduledAuction {
    private String auctionId;
    private LocalDateTime beginDate;
    private double startingPrice;
    private List<String> categories;

    public ScheduledAuction(String auctionId, LocalDateTime beginDate, double startingPrice, List<String> categories) {
        this.auctionId = auctionId;
        this.beginDate = beginDate;
        this.startingPrice = startingPrice;
        this.categories = categories;
    }

    public ScheduledAuction(AuctionDescription ad) {
        this.auctionId = ad.getAuctionId();
        this.beginDate = ad.getBeginDate();
        this.startingPrice = ad.getStartingPrice();
        this.categories = ad.getCategories();
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
