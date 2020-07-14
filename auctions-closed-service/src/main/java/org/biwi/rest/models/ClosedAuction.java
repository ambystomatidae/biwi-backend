package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class ClosedAuction extends PanacheEntityBase {

    @Id
    public String id;
    @Transient
    public List<Integer> bids;
    public String winnerId;
    public String sellerId;

    public ClosedAuction() {
    }

    public ClosedAuction(JSONObject json) {
        System.out.println("PROGRAMAR ESTE METODO");
    }

    public boolean isValidReviewer(String userId) {
        return sellerId.equals(userId) || winnerId.equals(userId);
    }

    @Override
    public String toString() {
        return "ClosedAuction{" +
                "id='" + id + '\'' +
                ", bids=" + bids +
                ", winnerId='" + winnerId + '\'' +
                ", selledId='" + sellerId + '\'' +
                '}';
    }
}
