package org.biwi.rest;

import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.models.AuctionDescriptionPostRequest;
import org.biwi.rest.models.ShortDescription;
import org.biwi.rest.models.StartingInfoResponse;
import org.biwi.rest.repositories.AuctionDescriptionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuctionDescriptionResource {

    @Inject
    AuctionDescriptionRepository repository;

    @GET
    @Path("/{auctionId}")
    public AuctionDescription getFull(@PathParam("auctionId") String auctionId) {
        return repository.getAuctionDescription(auctionId);
    }

    @GET
    @Path("/{auctionId}/short")
    public Response getShort(@PathParam("auctionId") String auctionId) {
        ShortDescription sd = repository.getShortDescription(auctionId);
        if (sd != null)
            return Response.ok(sd).build();
        else
            return Response.status(404).build();
    }

    @POST
    public Response add(AuctionDescriptionPostRequest desc) {
        AuctionDescription ad = new AuctionDescription(desc);
        // TODO: Upload the images to a bucket and store the url!
        boolean success = repository.add(ad);
        if (success)
            return Response.ok(ad).build();
        else
            return Response.status(400).build();
    }

    @GET
    @Path("/{auctionId}/startInfo")
    public Response getStartingInfo(@PathParam("auctionId") String auctionId) {
        StartingInfoResponse r = repository.getStartingInfo(auctionId);
        if (r != null) {
            return Response.ok(r).build();
        }
        else {
            return Response.status(404).build();
        }
    }
}