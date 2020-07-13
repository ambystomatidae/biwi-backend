package org.biwi.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.biwi.models.ScheduledAuction;
import org.biwi.requests.Filter;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
public class ScheduledAuctionRepository implements PanacheRepository<ScheduledAuction> {

    /**
     * @param auctionId id of the auction
     * @return the corresponding object or null, if doesn't exist
     */
    public ScheduledAuction getScheduled(String auctionId) {
        return find("auctionId", auctionId).firstResult();
    }

    /**
     * @param auctionId id of the auction
     * @return if the auction with this ID is scheduled
     */
    public boolean isScheduled(String auctionId) {
        return (find("auctionId", auctionId).count() > 0);
    }

    /**
     * @param a auction to persist
     */
    @Transactional
    public void addScheduled(ScheduledAuction a) {
        persist(a);
    }

    /**
     * @param range how soon is considered soon (in hours)
     * @param page which page of results to return
     * @param pageSize how many results should be in each page
     * @return List of auctions matching that criteria
     */
    public List<ScheduledAuction> getStartingSoon(int range, int page, int pageSize) {
        LocalDateTime endFilter = LocalDateTime.now()
                .plusHours(range);
        LocalDateTime startFilter = LocalDateTime.now();
        PanacheQuery<ScheduledAuction> query = find("from ScheduledAuction where beginDate < ?1 and beginDate > ?2", Sort.by("beginDate"), endFilter, startFilter);
        return query.page(Page.of(page, pageSize)).list();
    }

    /**
     * @param pageSize number of results returned
     * @param page if there are more results than pageSize, gets the page-th page.
     * @param filter object to filter results
     * @return List of scheduled auctions that meet given criteria
     */
    public List<ScheduledAuction> getAll(int pageSize, int page, Filter filter) {
        LocalDateTime startFilter = LocalDateTime.now();
        PanacheQuery<ScheduledAuction> query;
        if (filter != null && filter.byPrice()) {
            double lower = filter.getLowerPrice() != null ? filter.getLowerPrice() : 0;
            double higher = filter.getHigherPrice() != null ? filter.getHigherPrice() : Double.MAX_VALUE;
            query = find("from ScheduledAuction where beginDate > ?1 and startingPrice > ?2 and startingPrice < ?3", Sort.by("beginDate"), startFilter, lower, higher);
        }
        else {
            query = find("from ScheduledAuction where beginDate > ?1", Sort.by("beginDate"), startFilter);
        }
        return query.page(Page.of(page, pageSize)).list();
    }
}
