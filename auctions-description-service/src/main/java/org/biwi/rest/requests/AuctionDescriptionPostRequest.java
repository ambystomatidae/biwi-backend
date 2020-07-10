package org.biwi.rest.requests;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AuctionDescriptionPostRequest {
    private String auctionId;
    private String name;
    private double startingPrice;
    private double reservePrice;
    private String description;
    private LocalDateTime beginDate;
    private LocalTime duration;
    private List<String> images;
    private String mainImage;
    private List<String> categories;
    private String sellerId;

    public AuctionDescriptionPostRequest(String auctionId, String name, double startingPrice, double reservePrice, String description, LocalDateTime beginDate, LocalTime duration, List<String> images, String mainImage, List<String> categories, String sellerId) {
        this.auctionId = auctionId;
        this.name = name;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.description = description;
        this.beginDate = beginDate;
        this.duration = duration;
        this.images = images;
        this.mainImage = mainImage;
        this.categories = categories;
        this.sellerId = sellerId;
    }

    public AuctionDescriptionPostRequest() {
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
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

    public double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
