package org.biwi.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.CascadeType;



@Entity
public class AuctionsActive extends PanacheEntityBase {
    @Id private String id;
    private LocalTime duration;
    private double startingPrice;
    private double reservePrice;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bid> bids;
    private String sellerId;
    private double lastBidValue;
    @JsonIgnore
    @JsonbTransient
    private boolean open;


    public AuctionsActive(){
        this.bids = new ArrayList();
        this.open=true;
    }

    public AuctionsActive(String id, LocalTime duration, double startingPrice,double reservePrice, String sellerId){
        this.id = id;
        this.duration =duration;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.bids = new ArrayList();
        this.open=true;
        this.sellerId=sellerId;
        this.lastBidValue=startingPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
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

    public List<Bid> getBids() {
        return this.bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }


    public boolean addBid(Bid bid){
        return this.bids.add(bid);

    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        System.out.println(open);
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setLastBidValue(double lastBidValue) {
        this.lastBidValue = lastBidValue;
    }

    public double getLastBidValue() {
        return lastBidValue;
    }

    @JsonbTransient
    public Bid getLastBid(){
        int size=this.bids.size();
        if(size!=0){
            return this.bids.get(size-1);
        }
        return null;
    }


    @JsonbTransient
    public LocalDateTime getEndTimeAuction(){
        LocalTime t= this.duration;
        LocalDateTime nw= LocalDateTime.now().plusHours(1);
        LocalDateTime end= nw.plusHours(t.getHour()).plusMinutes(t.getMinute()).plusSeconds(t.getSecond()).plusNanos(t.getNano());
        return end;
    }
}