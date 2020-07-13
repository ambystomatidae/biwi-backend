package org.biwi.rest.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.biwi.rest.models.Auction;
import org.biwi.rest.models.BiwiUser;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<BiwiUser> {

    public BiwiUser findById(String id) {
        return find("id", id).firstResult();
    }

    public BiwiUser findByEmail(String email) {
        return  find("email", email).firstResult();
    }

    @Transactional
    public void removeFromWatchlist(Auction auction) {
        List<BiwiUser> users = findAll().list();
        users.forEach(u -> u.watchlist.remove(auction));
    }
}
