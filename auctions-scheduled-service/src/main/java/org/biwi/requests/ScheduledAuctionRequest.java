package org.biwi.requests;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduledAuctionRequest {
    private String auctionId;
    private LocalDateTime beginDate;
    private double startingPrice;
    private List<String> categories;
    private double reservePrice;
    private LocalTime duration;
    private String sellerId;

    public ScheduledAuctionRequest() {
    }

    public ScheduledAuctionRequest(String auctionId, LocalDateTime beginDate, double startingPrice, List<String> categories, double reservePrice, LocalTime duration, String sellerId) {
        this.auctionId = auctionId;
        this.beginDate = beginDate;
        this.startingPrice = startingPrice;
        this.categories = categories;
        this.reservePrice = reservePrice;
        this.duration = duration;
        this.sellerId = sellerId;
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

    public double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
