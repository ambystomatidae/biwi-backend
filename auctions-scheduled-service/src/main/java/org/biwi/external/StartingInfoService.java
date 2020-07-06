package org.biwi.external;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/v1")
@RegisterRestClient(configKey = "starting-info-api")
public interface StartingInfoService {

    @GET
    @Path("/{auctionId}/startInfo")
    @Produces("application/json")
    StartingInfo getStartingInfo(@PathParam("auctionId") String auctionId);
}
