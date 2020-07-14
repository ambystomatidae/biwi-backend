package org.biwi.rest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ShortDescription {
    public String auctionID;
    public String sellerID;
    public String name;
    public double startingPrice;
    public LocalDateTime beginDate;
    public String image;
    public double actualPrice;
    public LocalTime duration;
    public List<String> categories;



    public ShortDescription(){
    }

    public ShortDescription(String auctionID, String sellerID, String name, double startingPrice, LocalDateTime beginDate, String image, double actualPrice, LocalTime duration, List<String> categories) {
        this.auctionID = auctionID;
        this.sellerID= sellerID;
        this.name = name;
        this.startingPrice = startingPrice;
        this.beginDate = beginDate;
        this.image = image;
        this.actualPrice = actualPrice;
        this.duration = duration;
        this.categories = categories;
    }

    public String getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}