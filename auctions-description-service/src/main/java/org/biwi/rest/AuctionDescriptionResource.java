package org.biwi.rest;

import org.biwi.rest.external.AuctionsScheduledService;
import org.biwi.rest.external.ScheduledAuctionRequest;
import org.biwi.rest.models.AuctionDescription;
import org.biwi.rest.requests.AuctionDescriptionPostRequest;
import org.biwi.rest.requests.GetAllDescriptions;
import org.biwi.rest.responses.ShortDescription;
import org.biwi.rest.responses.StartingInfoResponse;
import org.biwi.rest.repositories.AuctionDescriptionRepository;
import org.biwi.rest.services.BucketManager;
import org.biwi.rest.services.ImageEncoderUtil;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuctionDescriptionResource {

    /**
     * Repository to manage ORM
     */
    @Inject
    AuctionDescriptionRepository repository;

    /**
     * Bean to upload images to GCloud
     */
    @Inject
    BucketManager bucketManager;

    /**
     * External service, used to schedule a new auction
     */
    @Inject
    @RestClient
    AuctionsScheduledService scheduledService;

    /**
     * Access Token
     */
    @Inject
    JsonWebToken jwt;

    /**
     * @param auctionId id of the auction to retrieve
     * @return returns the full description of the auction
     */
    @GET
    @Path("/{auctionId}")
    public Response getFull(@PathParam("auctionId") String auctionId) {
        AuctionDescription auction = repository.getAuctionDescription(auctionId);
        if (auction != null)
            return Response.ok(auction).build();
        else
            return Response.status(404).build();
    }

    /**
     * @param auctionId id of the auction to retrieve
     * @return returns the shortened description of the auction (if exists)
     */
    @GET
    @Path("/{auctionId}/short")
    public Response getShort(@PathParam("auctionId") String auctionId) {
        ShortDescription sd = repository.getShortDescription(auctionId);
        if (sd != null)
            return Response.ok(sd).build();
        else
            return Response.status(404).build();
    }

    /**
     * @param desc Request that contains info necessary to create a new AuctionDescription
     * @return What is stored as auction description
     */
    @POST
    public Response add(AuctionDescriptionPostRequest desc) {
        System.out.println("name:" + jwt.getName() + ". id: " + desc.getSellerId());
        if(!desc.getSellerId().equals(jwt.getName()))
            return Response.status(403).build();

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
        Response r = scheduledService.schedule(new ScheduledAuctionRequest(ad));
        System.out.println("Status: " + r.getStatus());
        if (success)
            return Response.ok(ad).build();
        else
            return Response.status(400).build();
    }
}