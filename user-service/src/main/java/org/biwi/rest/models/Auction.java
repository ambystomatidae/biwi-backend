package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Auction extends PanacheEntity {

    public String auctionId;

    public Auction() {
    }

    public Auction(String auctionId) {
        this.auctionId = auctionId;
    }

    public boolean isValid() {
        return auctionId != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        return auctionId != null ? auctionId.equals(auction.auctionId) : auction.auctionId == null;
    }

    @Override
    public int hashCode() {
        return auctionId != null ? auctionId.hashCode() : 0;
    }
}
