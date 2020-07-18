package org.biwi.rest.resource;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import org.biwi.rest.model.*;
import org.biwi.external.ShortDescriptionService;
import org.biwi.rest.messaging.*;
import org.biwi.rest.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.jwt.JsonWebToken;
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

    @Inject
    JsonWebToken jwt;


    @GET
    @Path("/{id}")
    public Response getByID(@PathParam("id") String id) {
        AuctionsActive a = auctActiveRepository.findById(id);
        if (a != null) {
            if (a.isOpen()) {
                return Response.ok(a).build();
            }
            return Response.status(403).build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/all")
    public Response getAll(@DefaultValue("0") @QueryParam("page") int page,
                           @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                           @DefaultValue("duration") @QueryParam("sortBy") String sortBy,
                           @DefaultValue("0") @QueryParam("lowerPrice") double lowerPrice,
                           @DefaultValue("200000000") @QueryParam("higherPrice") double higherPrice) {
        Filter filter = new Filter();
        filter.setHigherPrice(higherPrice);
        filter.setLowerPrice(lowerPrice);
        PanacheQuery query= auctActiveRepository.getAll(filter, sortBy,false);
        List<AuctionsActive> aa = query.page(Page.of(page, pageSize)).list();
        if ( aa!= null ) {
            if(!aa.isEmpty()) {
                List<ShortDescription> result = this.getShortDescription(aa);
                ShortDescriptionResponse response= new ShortDescriptionResponse(result,query.pageCount(),query.count());
                return Response.ok(response).build();
            }
            List<ShortDescription> sd = new ArrayList<>();
            return Response.ok(sd).build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/hotpicks")
    public Response getHotpicks(@DefaultValue("0") @QueryParam("page") int page,
                                @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                                @DefaultValue("sum(b.value) desc") @QueryParam("sortBy") String sortBy,
                                @DefaultValue("0") @QueryParam("lowerPrice") double lowerPrice,
                                @DefaultValue("200000000") @QueryParam("higherPrice") double higherPrice){
        Filter filter = new Filter();
        filter.setHigherPrice(higherPrice);
        filter.setLowerPrice(lowerPrice);
      PanacheQuery query= auctActiveRepository.getAll(filter, sortBy,true);
        List<AuctionsActive> aa = query.page(Page.of(page, pageSize)).list();
        if ( aa!= null ) {
            if(!aa.isEmpty()) {
                List<ShortDescription> result = this.getShortDescription(aa);
                ShortDescriptionResponse response= new ShortDescriptionResponse(result,query.pageCount(),query.count());
                return Response.ok(response).build();
            }
            List<ShortDescription> sd = new ArrayList<>();
            return Response.ok(sd).build();
        }
        return Response.status(400).build();
    }

    // Método para testar, eliminar depois
    @POST
    @Transactional
    public boolean addAuction(AuctionsActive auctionsActive) {
        AuctionsActive auction = new AuctionsActive(auctionsActive.getId(), LocalTime.parse("00:30:00"), auctionsActive.getStartingPrice(), auctionsActive.getReservePrice(), auctionsActive.getSellerId());
        auctActiveRepository.persist(auction);
        if (auctActiveRepository.isPersistent(auction)) {
            auctionBid.initiate(auction.getId(), auction.getStartingPrice());
            return true;
        }
        return false;
    }

    @POST
    @Path("/bid/{id}")
    @Transactional
    public Response addBid(@PathParam("id") String id, Bid b) {
        AuctionsActive aa = auctActiveRepository.validateBid(id, b.getValue());

        if (aa == null)
            return Response.status(404).build();

        if (aa.isOpen() || jwt.getName().equals(b.getIdUser())) {
            Bid bid = new Bid(b.getIdUser(), b.getValue());
            bid.persist();
            boolean status = auctActiveRepository.addBid(aa, bid);
            if (status) {
                auctionBid.produce(id, bid.getValue());
                return Response.ok(bid).build();
            }
            return Response.status(409).build();
        }
        return Response.status(403).build();
    }

    @DELETE
    @Path("/remove/{id}")
    public Response removeAuction(@PathParam("id") String id) {
        AuctionsActive aa = auctActiveRepository.removeAuction(id);
        if (aa != null) {
            return Response.ok(aa).build();
        }
        return Response.status(404).build();
    }

    private List<ShortDescription> getShortDescription (List<AuctionsActive> all){
        List<ShortDescription> result = new ArrayList<>();
        for (AuctionsActive a : all) {
            if (a.isOpen()) {
                ShortDescription sd = shortDescriptionService.getShortDescription(a.getId());
                if (sd != null) {
                    sd.setActualPrice(a.getLastBidValue());
                    result.add(sd);
                }
            }
        }
        return result;
    }
}
