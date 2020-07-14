package org.biwi.external;

import org.biwi.rest.model.ShortDescription;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/v1")
@RegisterRestClient(configKey = "auctions-description-api")
public interface ShortDescriptionService {

    @GET
    @Path("{auctionId}/short")
    @Produces("application/json")
    ShortDescription getShortDescription(@PathParam("auctionId") String auctionId);
}
