package org.biwi.external;

import java.io.Serializable;
import java.time.LocalTime;

public class StartingInfo implements Serializable {
    private String auctionId;
    private double startingPrice;
    private double reservePrice;
    private LocalTime duration;

    public StartingInfo() {
    }

    public StartingInfo(String auctionId, double startingPrice, double reservePrice, LocalTime duration) {
        this.auctionId = auctionId;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.duration = duration;
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
}