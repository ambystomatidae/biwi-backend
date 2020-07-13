package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Score extends PanacheEntity {
    public Double rating;
    public String auctionId;

    public Score() {
    }

    public static boolean isValidReview(String reviewerId, String reviewdId, String sellerId, String winnerId) {
        return (reviewerId.equals(sellerId) && reviewdId.equals(winnerId)) || (reviewerId.equals(winnerId) || reviewdId.equals(sellerId));
    }

    public boolean isValid() {
        return rating != null && auctionId != null && rating >= 0 && rating <=5 ;
    }

}
