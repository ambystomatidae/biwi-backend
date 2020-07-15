package org.biwi;

import org.biwi.external.ShortDescription;
import org.biwi.external.StartingInfo;
import org.biwi.external.AuctionsDescriptionService;
import org.biwi.requests.AllStartingRequestObject;
import org.biwi.models.ScheduledAuction;
import org.biwi.requests.Filter;
import org.biwi.requests.ScheduledAuctionRequest;
import org.biwi.requests.StartingSoonRequestObject;
import org.biwi.repositories.ScheduledAuctionRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ScheduledResource {

    @Inject
    ScheduledAuctionRepository repository;

    @Inject
    EventProducer producer;

    @Inject
    @RestClient
    AuctionsDescriptionService auctionsDescriptionService;

    /**
     * Schedule an auction to start at a given date
     * @param auc auctionId and auction StartDate
     * @return Ok with the scheduling date
     */
    @POST
    @Path("schedule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response schedule(ScheduledAuctionRequest auc) {
        ScheduledAuction ac = new ScheduledAuction();
        ac.setAuctionId(auc.getAuctionId());
        ac.setBeginDate(auc.getBeginDate());
        ac.setStartingPrice(auc.getStartingPrice());
        ac.setCategories(auc.getCategories());
        try {
            repository.addScheduled(ac);
        }
        catch (Exception e) {
            // duplicate key
            return Response.status(403).build();
        }
        StartingInfo si = new StartingInfo();
        si.setAuctionId(auc.getAuctionId());
        si.setStartingPrice(auc.getStartingPrice());
        si.setReservePrice(auc.getReservePrice());
        si.setDuration(auc.getDuration());
        si.setSellerId(auc.getSellerId());
        producer.produce(si, auc.getBeginDate());
        return Response.ok(si).build();
    }

    /**
     * @param soon Contains limit of scheduledauctions to return, and the temporal range
     * @return List<ScheduledAuction> (may be empty)
     */
    @GET
    @Path("soon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingSoon(StartingSoonRequestObject soon) {
        int range = soon.getRange();
        int page = soon.getPage();
        int pageSize = soon.getPageSize();
        List<ScheduledAuction> startingSoon = repository.getStartingSoon(range, page, pageSize);
        List<ShortDescription> result = new ArrayList<>();
        for(ScheduledAuction s : startingSoon) {
            result.add(auctionsDescriptionService.getShortDescription(s.getAuctionId()));
        }
        return Response.ok(result).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("categories") List<String> categories,
                           @DefaultValue("0") @QueryParam("page") int page,
                           @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                           @DefaultValue("beginDate") @QueryParam("sortBy") String sortBy,
                           @DefaultValue("0") @QueryParam("lowerPrice") double lowerPrice,
                           @DefaultValue("200000000") @QueryParam("higherPrice") double higherPrice) {
        Filter filter = new Filter();
        filter.setCategories(categories);
        filter.setHigherPrice(higherPrice);
        filter.setLowerPrice(lowerPrice);
        List<ScheduledAuction> allScheduled = repository.getAll(pageSize, page, filter, sortBy);
        List<ShortDescription> result = new ArrayList<>();
        for(ScheduledAuction s : allScheduled) {
            ShortDescription sd = auctionsDescriptionService.getShortDescription(s.getAuctionId());
            result.add(sd);
        }
        return Response.ok(result).build();
    }
}
