package org.biwi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.biwi.requests.ScheduledAuctionRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "begin_date_idx", columnList = "begindate"),
        @Index(name = "starting_price_idx", columnList = "startingprice")
})
public class ScheduledAuction extends PanacheEntityBase {
    @Id
    private String auctionId;
    @Column(name = "begindate")
    private LocalDateTime beginDate;
    @Column(name = "startingprice")
    private double startingPrice;
    @ElementCollection
    private List<String> categories;

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
