package org.biwi;

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
    private ScheduledAuctionRepository repository;

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
        // produce scheduled message here TODO
        return Response.ok(auc).build();
    }

    @GET
    @Path("soon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingSoon(StartingSoonRequestObject soon) {
        int limit = soon.getLimit();
        int range = soon.getRange();
        List<ScheduledAuction> startingSoon = repository.getStartingSoon(range, limit);
        return Response.ok(startingSoon).build();
    }
}
