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
    public String name;
    public String winnerId;
    public String sellerId;
    public double topBid;
    public double sellerRating;
    public double winnerRating;

    public ClosedAuction() {
    }

    public ClosedAuction(String auctionId, String name, JSONObject json) {
        this.id = auctionId;
        this.name = name;
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

    public boolean isValidReview(String userId1, String userId2){
        return (winnerId.equals(userId1) && sellerId.equals(userId2)) || ((winnerId.equals(userId2) && sellerId.equals(userId1)));
    }

    public void addReview(String reviewerId, Score score){
        if(winnerId.equals(reviewerId) && sellerId.equals(score.userId))
            this.sellerRating = score.rating;
        else if ((winnerId.equals(score.userId) && sellerId.equals(reviewerId)))
            this.winnerRating = score.rating;
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
