package org.biwi.rest.responses;

import org.biwi.rest.models.AuctionDescription;

import java.time.LocalTime;

public class StartingInfoResponse {
    private String auctionId;
    private double startingPrice;
    private double reservePrice;
    private LocalTime duration;
    private String sellerId;

    public StartingInfoResponse(String auctionId, double startingPrice, double reservePrice, LocalTime duration, String sellerId) {
        this.auctionId = auctionId;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.duration = duration;
        this.sellerId = sellerId;
    }

    public StartingInfoResponse(AuctionDescription ad) {
        this.auctionId = ad.getAuctionId();
        this.startingPrice = ad.getStartingPrice();
        this.reservePrice = ad.getReservePrice();
        this.duration = ad.getDuration();
        this.sellerId = ad.getSellerId();
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
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
