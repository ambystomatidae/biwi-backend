package org.biwi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "begin_date_idx", columnList = "begindate")
})
public class ScheduledAuction extends PanacheEntityBase {
    @Id
    private String auctionId;
    @Column(name = "begindate")
    private LocalDateTime beginDate;

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
}
