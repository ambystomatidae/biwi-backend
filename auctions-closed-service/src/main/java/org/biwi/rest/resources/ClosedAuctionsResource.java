package org.biwi.rest.resources;

import org.biwi.rest.models.ClosedAuction;
import org.biwi.rest.models.Score;
import org.biwi.rest.repositories.ClosedAuctionsRepository;
import org.biwi.rest.util.RequestsHandler;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClosedAuctionsResource {

    @Inject
    ClosedAuctionsRepository closedAuctionsRepository;

    @Inject
    JsonWebToken accessToken;

    RequestsHandler requestsHandler = RequestsHandler.getInstance();

    // Método de teste. Eliminar depois
    @POST
    @Transactional
    @Path("/auction")
    public Response postClosedAuction(ClosedAuction auction) {
        closedAuctionsRepository.persist(auction);
        return Response.ok().build();
    }

    // Método de teste. Eliminar depois
    @GET
    @Path("/auction")
    public Response getClosedAuctions() {
        List<ClosedAuction> auctions =  closedAuctionsRepository.findAll().list();
        return Response.ok(auctions).build();
    }

    @GET
    @Path("/auction/{auctionId}")
    public Response getClosedAuction(@PathParam("auctionId") String auctionId) {
        ClosedAuction auction = closedAuctionsRepository.findById(auctionId);

        if (auction == null)
            return Response.status(404).build();

        return Response.ok(auction).build();
    }

    @GET
    @Path("/user")
    public List<ClosedAuction> getUserAuctions() {
        return getUserAuctionsById(accessToken.getName());
    }

    @GET
    @Path("/user/{userId}")
    public List<ClosedAuction> getUserAuctionsById(@PathParam("userId") String userId) {
        return closedAuctionsRepository.listUserAuctions(userId);
    }

    @POST
    @Transactional
    @Path("/review/{auctionId}")
    public Response addReview(@PathParam("auctionId") String auctionId, Score score) throws IOException {
        if (!score.isValid())
            return Response.status(400).build();

        ClosedAuction auction = closedAuctionsRepository.findById(auctionId);

        if (auction == null)
            return Response.status(404).build();
        
        if(!auction.isValidReview(accessToken.getName(), score.userId))
            return Response.status(403).build();

        score.auctionId = auctionId;

        requestsHandler.addToUserScore(score, accessToken.getName());
        auction.addReview(accessToken.getName(), score);

        return Response.ok().build();
    }

}