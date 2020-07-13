package org.biwi.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
    private boolean open;

    public AuctionsActive(){
        this.bids = new ArrayList();
        this.open=true;
    }

    public AuctionsActive(String id, LocalTime duration, double startingPrice,double reservePrice){
        this.id = id;
        this.duration =duration;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.bids = new ArrayList();
        this.open=true;
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

    @JsonIgnore
    public Bid getLastBid(){
        int size=this.bids.size();
        if(size!=0){
            return this.bids.get(size-1);
        }
        return null;
    }

    @JsonIgnore
    public double getLastBidValue(){
        if(this.getLastBid() !=null){
            return this.getLastBid().getValue();
        }
        return this.startingPrice;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        System.out.println(open);
    }

    @JsonIgnore
    public LocalDateTime endAuction(){
        LocalTime t= this.duration;
        LocalDateTime nw= LocalDateTime.now();
        LocalDateTime end= nw.plusHours(t.getHour()).plusMinutes(t.getMinute()).plusSeconds(t.getSecond()).plusNanos(t.getNano());
        System.out.println(end);
        return end;

    }

}