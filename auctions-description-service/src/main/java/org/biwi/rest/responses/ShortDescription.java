package org.biwi.rest.responses;

import org.biwi.rest.models.AuctionDescription;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ShortDescription {
    private String name;
    private double startingPrice;
    private LocalDateTime beginDate;
    private String mainImage;
    private LocalTime duration;
    private List<String> categories;

    public ShortDescription(String name, double startingPrice, LocalDateTime beginDate, String mainImage, LocalTime duration, List<String> categories) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.beginDate = beginDate;
        this.mainImage = mainImage;
        this.duration = duration;
        this.categories = categories;
    }

    public ShortDescription(AuctionDescription e) {
        this.name = e.getName();
        this.startingPrice = e.getStartingPrice();
        this.beginDate = e.getBeginDate();
        this.mainImage = e.getMainImage();
        this.duration = e.getDuration();
        this.categories = e.getCategories();
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
