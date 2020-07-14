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
    public int ratingsCounter;
    public double currentRating;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Auction> watchlist;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<Score> scores;
    // Dados guardados no Keycloak
    public String email;
    @Transient
    public String password;
    @Transient
    public boolean emailVerified;
    @Transient
    public String firstName;
    @Transient
    public String lastName;

    public BiwiUser() {
    }

    public BiwiUser(String id, String email) {
        this.id = id;
        this.email = email;
        this.watchlist = new ArrayList<>();
        this.currentRating = 0;
        this.scores = new HashSet<>();
    }

    public void addToWatchlist(Auction auction) {
        this.watchlist.add(auction);
    }

    public void removeFromWatchlist(Auction auction) {
        this.watchlist.remove(auction);
    }

    public void addToScore(Score score) {
        this.scores.removeIf(score1 -> score.auctionId == score1.auctionId);
        this.scores.add(score);
        this.currentRating = scores.stream().mapToDouble(s -> s.rating).sum() / scores.size();
    }

    public void addKeycloakInfo(JSONObject keycloakUser) {
        this.email = (String) keycloakUser.get("email");
        this.emailVerified = (Boolean) keycloakUser.get("emailVerified");
        this.firstName = (String) keycloakUser.get("firstName");
        this.lastName = (String) keycloakUser.get("lastName");
    }

    public boolean isValid() {
        return this.email != null && this.password != null && this.firstName != null && this.lastName != null;
    }


    @Override
    public String toString() {
        return "BiwiUser{" +
                "id='" + id + '\'' +
                ", ratingsCounter=" + ratingsCounter +
                ", currentRating=" + currentRating +
                ", watchlist=" + watchlist +
                ", scores=" + scores +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}