package org.biwi.rest.resources;

import org.biwi.rest.models.ClosedAuction;
import org.biwi.rest.repositories.ClosedAuctionsRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClosedAuctionsResource {

    @Inject
    ClosedAuctionsRepository closedAuctionsRepository;

    @POST
    @Transactional
    @RolesAllowed("admin")
    @Path("/auction")
    public Response postClosedAuction(ClosedAuction auction) {
        closedAuctionsRepository.persist(auction);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/auction")
    public Response getClosedAuctions() {
        List<ClosedAuction> auctions =  closedAuctionsRepository.findAll().list();
        return Response.ok(auctions).build();
    }

    @GET
    @RolesAllowed("user")
    @Path("/auction/{auctionId}")
    public Response getClosedAuction(@PathParam("auctionId") String auctionId) {
        ClosedAuction auction = closedAuctionsRepository.findById(auctionId);

        if (auction == null)
            return Response.status(404).build();

        return Response.ok(auction).build();
    }


    @GET
    @RolesAllowed("user")
    @Path("/user/{userId}")
    public List<ClosedAuction> getUserAuctions(@PathParam("userId") String userId) {
        return closedAuctionsRepository.listUserAuctions(userId);
    }
}