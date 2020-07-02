package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AuctionDescription extends PanacheEntityBase {
    @Id
    private String auctionId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double startingPrice;
    @Column(nullable = false)
    private double reservePrice;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime beginDate;
    @Column(nullable = false)
    private LocalTime duration;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToOne(cascade = CascadeType.ALL)
    private Image mainImage;
    @ElementCollection
    private List<String> categories;
    @Column(nullable = false)
    private String sellerId;

    public AuctionDescription() {
        this.auctionId = null;
        this.name = "";
        this.startingPrice = 0;
        this.reservePrice = 0;
        this.description = "";
        this.beginDate = LocalDateTime.now();
        this.duration = LocalTime.of(1,0);
        this.images = new ArrayList<>();
        this.mainImage = null;
        this.categories = new ArrayList<>();
        this.sellerId = null;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public void setMainImage(Image mainImage) {
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
