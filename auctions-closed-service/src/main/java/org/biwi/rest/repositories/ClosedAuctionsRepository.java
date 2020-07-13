package org.biwi.rest.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.biwi.rest.models.ClosedAuction;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClosedAuctionsRepository implements PanacheRepository<ClosedAuction> {
    public ClosedAuction findById(String auctionId) {
        return find("id", auctionId).firstResult();
    }

    public List<ClosedAuction> listUserAuctions(String userId) {
        return list("winnerid = ?1 or sellerid = ?1", userId);
    }

    @Transactional
    public void persistAuction(ClosedAuction auction){
        persist(auction);
    }
}
