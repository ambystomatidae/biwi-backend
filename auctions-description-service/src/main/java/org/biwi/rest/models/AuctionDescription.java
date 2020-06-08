package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class AuctionDescription extends PanacheEntity implements ShortDescription {
    @Column(unique = true)
    private String auctionId;
    private String name;
    private double startingPrice;
    private double reservePrice;
    private String description;
    private LocalDateTime beginDate;
    private LocalTime duration;
    @OneToMany
    private List<Image> images;
    @OneToOne
    private Image mainImage;
    @ElementCollection
    private List<String> categories;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getStartingPrice() {
        return this.startingPrice;
    }

    @Override
    public LocalDateTime getBeginDate() {
        return this.beginDate;
    }

    @Override
    public Image getMainImage() {
        return this.mainImage;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }
}
