package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Score extends PanacheEntity {
    public Double rating;
    public String userId;
    public String auctionId;

    public Score() {
    }

    public boolean isValid() {
        return rating != null && userId != null && rating >= 0 && rating <=5 ;
    }

}
