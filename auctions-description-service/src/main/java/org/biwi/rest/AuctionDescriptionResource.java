package org.biwi.rest;

import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.models.ShortDescription;
import org.biwi.rest.repositories.AuctionDescriptionRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuctionDescriptionResource {

    @Inject
    private AuctionDescriptionRepository repository;

    @GET
    @Path("/{auctionId}")
    public AuctionDescription getFull(@PathParam("auctionId") String auctionId) {
        return repository.find("auctionId", auctionId).firstResult();
    }

    @GET
    @Path("/{auctionId}/short")
    public ShortDescription getShort(@PathParam("auctionId") String auctionId) {
        return repository.find("auctionId", auctionId).firstResult();
    }

    @POST
    @Transactional
    public Response add(AuctionDescription desc) {
        if (desc != null && desc.getAuctionId() != null) {
            AuctionDescription ac = repository.find("auctionId", desc.getAuctionId()).firstResult();
            if (ac == null) {
                repository.persist(desc);
                return Response.created(URI.create(desc.getAuctionId())).build();
            }
            else {
                return Response.status(409).build();
            }
        }
        else {
            return Response.status(400).build();
        }
    }
}