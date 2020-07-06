package org.biwi.rest.responses;

import org.biwi.rest.models.AuctionDescription;

import java.time.LocalDateTime;

public class ShortDescription {
    private String name;
    private double startingPrice;
    private LocalDateTime beginDate;
    private String mainImage;

    public ShortDescription(String name, double startingPrice, LocalDateTime beginDate, String mainImage) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.beginDate = beginDate;
        this.mainImage = mainImage;
    }

    public ShortDescription(AuctionDescription e) {
        this.name = e.getName();
        this.startingPrice = e.getStartingPrice();
        this.beginDate = e.getBeginDate();
        this.mainImage = e.getMainImage();
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
}
