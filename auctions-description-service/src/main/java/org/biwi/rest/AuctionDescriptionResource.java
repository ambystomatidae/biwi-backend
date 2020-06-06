package org.biwi.rest;

import org.biwi.rest.models.AuctionDescription;
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
    public AuctionDescription get(@PathParam("auctionId") String auctionId) {
        return repository.find("auctionId", auctionId).firstResult();
    }

    @POST
    @Transactional
    public Response add(AuctionDescription desc) {
        repository.persist(desc);
        if (desc.auctionId != null && repository.isPersistent(desc)) {
            return Response.created(URI.create(desc.auctionId)).build();
        }
        else {
            return Response.status(400).build();
        }
    }
}