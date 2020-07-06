package org.biwi.rest.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.responses.ShortDescription;
import org.biwi.rest.responses.StartingInfoResponse;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
public class AuctionDescriptionRepository implements PanacheRepository<AuctionDescription> {
    public ShortDescription getShortDescription(String auctionId) {
        AuctionDescription ad = find("auctionId", auctionId).firstResult();
        if (ad != null)
            return new ShortDescription(ad);
        else
            return null;
    }

    public AuctionDescription getAuctionDescription(String auctionId) {
        return find("auctionId", auctionId).firstResult();
    }

    @Transactional
    public boolean add(AuctionDescription desc) {
        boolean result = false;
        if (desc != null && desc.getAuctionId() != null) {
            AuctionDescription ac = find("auctionId", desc.getAuctionId()).firstResult();
            if (ac == null) {
                persist(desc);
                result = true;
            }
        }
        return result;
    }

    public StartingInfoResponse getStartingInfo(String auctionId) {
        AuctionDescription ad = find("auctionId", auctionId).firstResult();
        if (ad != null) {
            return new StartingInfoResponse(ad);
        }
        else
            return null;
    }
}
