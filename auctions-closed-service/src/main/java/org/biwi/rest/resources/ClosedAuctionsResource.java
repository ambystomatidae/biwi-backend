package org.biwi.rest.resources;

import org.biwi.rest.models.ClosedAuction;
import org.biwi.rest.models.Score;
import org.biwi.rest.repositories.ClosedAuctionsRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

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

    @Inject
    JsonWebToken accessToken;

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

    @POST
    @Transactional
    @RolesAllowed("user")
    @Path("/review/{auctionId}")
    public Response postAuctionRating(@PathParam("auctionId") String auctionId, Score score) {
        if (!score.isValid())
            return Response.status(400).build();

        ClosedAuction auction = closedAuctionsRepository.findById(auctionId);

        if (auction == null)
            return Response.status(404).build();

        if (!auction.isValidReviewer(accessToken.getName()))
            return Response.status(403).build();

        auction.classification = score.rating;

        return Response.ok().build();
    }

    @GET
    @RolesAllowed("user")
    @Path("/user/{userId}")
    public List<ClosedAuction> getUserAuctions(@PathParam("userId") String userId) {
        return closedAuctionsRepository.listUserAuctions(userId);
    }
}