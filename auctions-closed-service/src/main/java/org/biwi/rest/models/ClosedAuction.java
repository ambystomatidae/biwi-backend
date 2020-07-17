package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.TreeSet;

@Entity
public class ClosedAuction extends PanacheEntityBase {

    @Id
    public String id;
    public String winnerId;
    public String sellerId;
    public double topBid;

    public ClosedAuction() {
    }

    public ClosedAuction(String auctionId, JSONObject json) {
        this.id = auctionId;
        this.sellerId = (String) json.get("sellerId");
        TreeSet<Bid> bids = new TreeSet<>((b1, b2) -> (int) (b2.value - b1.value));
        JSONArray bidsArray = (JSONArray) json.get("bids");
        bidsArray.forEach(b -> {
            JSONObject bid = (JSONObject) b;
            bids.add(new Bid((String)bid.get("idUser"), (Double) bid.get("value")));
        });
        Double reservePrice = (Double) json.get("reservePrice");
        this.winnerId = (!bids.isEmpty() && bids.first().value >= reservePrice) ? bids.first().idUser : "" ;
        this.topBid = (!bids.isEmpty()) ? bids.first().value : 0;
    }


    @Override
    public String toString() {
        return "ClosedAuction{" +
                "id='" + id + '\'' +
                ", winnerId='" + winnerId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", topBid=" + topBid +
                '}';
    }
}
