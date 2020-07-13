package org.biwi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
