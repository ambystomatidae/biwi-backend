package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class BiwiUser extends PanacheEntityBase {

    @Id
    public String id;
    @Transient
    public Credentials credentials;
    public int ratingsCounter;
    public double currentRating;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Auction> watchlist;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<Score> scores;
    // Dados guardados no Keycloak
    public String username;

    public String email;
    @Transient
    public boolean emailVerified;

    public BiwiUser() {
    }

    public BiwiUser(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.watchlist = new ArrayList<>();
        this.currentRating = 0;
        this.scores = new HashSet<>();
    }

    public void addToWatchlist(Auction auction) {
        this.watchlist.add(auction);
    }

    public void addToScore(Score score) {
        this.scores.removeIf(score1 -> score.auctionId == score1.auctionId);
        this.scores.add(score);
        this.currentRating = scores.stream().mapToDouble(s -> s.rating).sum() / scores.size();
    }

    public void addKeycloakInfo(JSONObject keycloakUser) {
        this.email = (String) keycloakUser.get("email");
        this.emailVerified = (Boolean) keycloakUser.get("emailVerified");
    }

    public boolean isValid() {
        return credentials != null && credentials.isValid() && credentials.username != null;
    }

    @Override
    public String toString() {
        return "BiwiUser{" +
                "credentials=" + credentials +
                ", ratingsCounter=" + ratingsCounter +
                ", currentRating=" + currentRating +
                ", watchlist=" + watchlist +
                '}';
    }
}