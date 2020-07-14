package org.biwi.rest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ShortDescription {
    public String auctionId;
    public String sellerId;
    public String name;
    public double startingPrice;
    public LocalDateTime beginDate;
    public String mainImage;
    public double actualPrice;
    public LocalTime duration;
    public List<String> categories;



    public ShortDescription(){
    }

    public ShortDescription(String auctionId, String sellerId, String name, double startingPrice, LocalDateTime beginDate, String mainImage, double actualPrice, LocalTime duration, List<String> categories) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.name = name;
        this.startingPrice = startingPrice;
        this.beginDate = beginDate;
        this.mainImage = mainImage;
        this.actualPrice = actualPrice;
        this.duration = duration;
        this.categories = categories;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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