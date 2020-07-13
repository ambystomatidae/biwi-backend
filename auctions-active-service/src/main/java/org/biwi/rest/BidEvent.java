package org.biwi.rest;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BidEvent implements Serializable {

    private String auctionId;
    private double value;
    private LocalDateTime timeStamp;
    private String idUser;

    public BidEvent(){
    }

    public BidEvent(String auctionId, double value, LocalDateTime timeStamp, String idUser) {
        this.auctionId = auctionId;
        this.value = value;
        this.timeStamp = timeStamp;
        this.idUser = idUser;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
