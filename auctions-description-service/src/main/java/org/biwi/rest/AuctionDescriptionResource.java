package org.biwi.rest;

import org.biwi.rest.external.AuctionsScheduledService;
import org.biwi.rest.external.ScheduledAuction;
import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.requests.AuctionDescriptionPostRequest;
import org.biwi.rest.requests.GetAllDescriptions;
import org.biwi.rest.responses.ShortDescription;
import org.biwi.rest.responses.StartingInfoResponse;
import org.biwi.rest.repositories.AuctionDescriptionRepository;
import org.biwi.rest.services.BucketManager;
import org.biwi.rest.services.ImageEncoderUtil;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuctionDescriptionResource {

    @Inject
    AuctionDescriptionRepository repository;

    @Inject
    BucketManager bucketManager;

    @Inject
    @RestClient
    AuctionsScheduledService scheduledService;

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
        UUID fileId = UUID.randomUUID();
        String uri = bucketManager.storeImage(ImageEncoderUtil.decode(desc.getMainImage()), fileId.toString() + ".jpeg");
        ad.setMainImage(uri);
        List<String> imgs = new ArrayList<>();
        for(String img : desc.getImages()) {
            UUID imgId = UUID.randomUUID();
            String imgUri = bucketManager.storeImage(ImageEncoderUtil.decode(img), imgId.toString() + ".jpeg");
            imgs.add(imgUri);
        }
        ad.setImages(imgs);
        boolean success = repository.add(ad);
        Response r = scheduledService.schedule(new ScheduledAuction(ad));
        System.out.println("Status: " + r.getStatus());
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