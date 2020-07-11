package org.biwi;

import org.biwi.external.ShortDescription;
import org.biwi.external.StartingInfo;
import org.biwi.external.AuctionsDescriptionService;
import org.biwi.requests.AllStartingRequestObject;
import org.biwi.models.ScheduledAuction;
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
    public Response schedule(ScheduledAuction auc) {
        StartingInfo si = auctionsDescriptionService.getStartingInfo(auc.getAuctionId());
        if (si == null)
            return Response.status(404).build();
        try {
            repository.addScheduled(auc);
        }
        catch (Exception e) {
            // duplicate key
            return Response.status(403).build();
        }
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
    public Response getAll(AllStartingRequestObject all) {
        int pageSize = all.getPageSize();
        int page = all.getPage();
        List<ScheduledAuction> allScheduled = repository.getAll(pageSize, page);
        List<ShortDescription> result = new ArrayList<>();
        for(ScheduledAuction s : allScheduled) {
            result.add(auctionsDescriptionService.getShortDescription(s.getAuctionId()));
        }
        return Response.ok(result).build();
    }
}
