package org.biwi;

import org.biwi.models.AllStartingRequestObject;
import org.biwi.models.ScheduleAuctionEvent;
import org.biwi.models.ScheduledAuction;
import org.biwi.models.StartingSoonRequestObject;
import org.biwi.repositories.ScheduledAuctionRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduledResource {

    @Inject
    ScheduledAuctionRepository repository;

    @Inject
    EventProducer producer;

    @POST
    @Path("schedule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response schedule(ScheduledAuction auc) {
        try {
            repository.addScheduled(auc);
        }
        catch (Exception e) {
            // duplicate key
            return Response.status(403).build();
        }
        producer.produce(new ScheduleAuctionEvent(auc), auc.getBeginDate());
        return Response.ok(auc).build();
    }

    /**
     * @param soon Contains limit of scheduledauctions to return, and the temporal range
     * @return List<ScheduledAuction> (may be empty)
     */
    @GET
    @Path("soon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingSoon(StartingSoonRequestObject soon) {
        int limit = soon.getLimit();
        int range = soon.getRange();
        List<ScheduledAuction> startingSoon = repository.getStartingSoon(range, limit);
        return Response.ok(startingSoon).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(AllStartingRequestObject all) {
        int pageSize = all.getPageSize();
        int page = all.getPage();
        List<ScheduledAuction> allScheduled = repository.getAll(pageSize, page);
        return Response.ok(allScheduled).build();
    }
}
