package org.biwi.rest.models;

import java.time.LocalDateTime;

public class ShortDescription {
    private String name;
    private double startingPrice;
    private LocalDateTime beginDate;
    private Image mainImage;

    public ShortDescription(String name, double startingPrice, LocalDateTime beginDate, Image mainImage) {
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

    public Image getMainImage() {
        return mainImage;
    }

    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }
}
