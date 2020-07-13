package org.biwi.external;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShortDescription {
    private String name;
    private double startingPrice;
    private LocalDateTime beginDate;
    private String mainImage;
    private LocalTime duration;

    public ShortDescription() {
    }

    public ShortDescription(String name, double startingPrice, LocalDateTime beginDate, String mainImage, LocalTime duration) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.beginDate = beginDate;
        this.mainImage = mainImage;
        this.duration = duration;
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

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
}
