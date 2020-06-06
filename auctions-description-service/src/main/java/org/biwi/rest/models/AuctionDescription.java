package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class AuctionDescription extends PanacheEntity implements ShortDescription {
    public String auctionId;
    public String name;
    public double startingPrice;
    public double reservePrice;
    public String description;
    public LocalDateTime beginDate;
    public LocalTime duration;
    @OneToMany
    public List<Image> images;
    @OneToOne
    public Image mainImage;
    @ElementCollection
    public List<String> categories;

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
}
