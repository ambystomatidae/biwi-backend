package org.biwi.rest.external;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/v1")
@RegisterRestClient(configKey = "auctions-scheduled-api")
public interface AuctionsScheduledService {

    @POST
    @Path("schedule")
    @Produces("application/json")
    Response schedule(ScheduledAuctionRequest auction);
}
