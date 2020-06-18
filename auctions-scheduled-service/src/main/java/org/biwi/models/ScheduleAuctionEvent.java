package org.biwi.models;

import java.io.Serializable;
import java.time.LocalTime;

public class ScheduleAuctionEvent implements Serializable {
    private String auctionId;
    private double startingPrice;
    private double reservePrice;
    private LocalTime duration;

    public ScheduleAuctionEvent(String auctionId, double startingPrice, double reservePrice, LocalTime duration) {
        this.auctionId = auctionId;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.duration = duration;
    }

    public ScheduleAuctionEvent(ScheduledAuction au) {
        this.auctionId = au.getAuctionId();
        this.startingPrice = au.getStartingPrice();
        this.reservePrice = au.getReservePrice();
        this.duration = au.getDuration();
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
