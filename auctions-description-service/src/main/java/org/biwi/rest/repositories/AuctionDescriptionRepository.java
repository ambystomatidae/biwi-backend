package org.biwi.rest.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.models.ShortDescription;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuctionDescriptionRepository implements PanacheRepository<AuctionDescription> {
    public ShortDescription getShortDescription(String auctionId) {
        return find("auctionId", auctionId).firstResult();
    }
}
