package org.biwi.rest.resource;

import org.biwi.external.ShortDescriptionService;
import org.biwi.rest.model.Bid;
import org.biwi.rest.model.ShortDescription;
import org.biwi.rest.messaging.*;
import org.biwi.rest.*;
import org.biwi.rest.model.AuctionsActive;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.core.Response;

@Path("/active")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuctionsActiveResource {

    @Inject
    AuctionsActiveRepository auctActiveRepository;

    @Inject
    AuctionBidProducer auctionBid;

    @Inject
    @RestClient
    ShortDescriptionService shortDescriptionService;


    @GET
    @Path("/{id}")
    public Response getByID(@PathParam("id") String id){
        AuctionsActive a = auctActiveRepository.findById(id);
        if (a != null){
            if(a.isOpen()){
                return Response.ok(a).build();
            }
            return Response.status(403).build();
        }
        return Response.status(400).build();
    }

    
    @GET
    @Path("/all")
    public Response getAll(){
        List<AuctionsActive> all = auctActiveRepository.listAll();
        if(all!=null){
            List<ShortDescription> result = new ArrayList<>();
            for(AuctionsActive a: all){
                if(a.isOpen()){
                    ShortDescription sd = shortDescriptionService.getShortDescription(a.getId());
                    if(sd!=null) {
                        sd.setActualPrice(a.getLastBidValue());
                        result.add(sd);
                    }
                }
            }
            return Response.ok(result).build();
        }
        return Response.status(400).build();
    }


    @GET
    @Path("/hotpicks")
    public Response getHotpicks(){
        List<AuctionsActive> aa= auctActiveRepository.getHotpicks();
        return Response.ok(aa).build();
    }

   @POST
   @Transactional
   public boolean addAuction(AuctionsActive auctionsActive){
       AuctionsActive auction = new AuctionsActive(auctionsActive.getId(),LocalTime.parse("00:30:00"),auctionsActive.getStartingPrice(),auctionsActive.getReservePrice(), auctionsActive.getSellerId());
       auctActiveRepository.persist(auction);
       if(auctActiveRepository.isPersistent(auction)){
           return true;
       }
       return false;
   }

   @POST
   @Path("/bid/{id}")
   @Transactional
   public Response addBid(@PathParam("id") String id,Bid b){
        AuctionsActive aa = auctActiveRepository.validateBid(id,b.getValue());
        if(aa!=null && aa.isOpen()){
            Bid bid = new Bid(b.getIdUser(),b.getValue());
            bid.persist();
            boolean status= auctActiveRepository.addBid(aa,id,bid);
            if (status){
                auctionBid.produce(bid,id);
                return Response.status(200).build(); 
            }
            return Response.status(409).build(); 
        }
        return Response.status(403).build();
   }

    @DELETE
    @Path("/remove/{id}")
    public Response removeAuction(@PathParam("id") String id){
        AuctionsActive aa = auctActiveRepository.removeAuction(id);
        if(aa!=null){
            return Response.ok(aa).build();
        }
        return Response.status(400).build();
    }

}