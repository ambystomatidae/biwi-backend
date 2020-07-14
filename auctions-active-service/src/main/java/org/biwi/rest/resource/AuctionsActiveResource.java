package org.biwi.rest.resource;



import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.biwi.rest.model.Bid;
import org.biwi.rest.model.ShortDescription;
import org.biwi.rest.producer.*;
import org.biwi.rest.*;
import org.biwi.external.*;
import org.biwi.rest.model.AuctionsActive;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;

@Path("/active")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuctionsActiveResource implements Runnable {

    @Inject
    AuctionsActiveRepository auctActiveRepository;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    PrepareToCloseProducer prepareToClose;



    @Inject
    AuctionBidProducer auctionBid;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @GET
    @Path("/{id}")
    public Response getByID(@PathParam("id") String id){
        AuctionsActive a = auctActiveRepository.findById(id);
        if (a != null)
            return Response.ok(a).build();
        return Response.status(400).build();
    }

    
    @GET
    @Path("/all")
    public Response getAll(){
        List<ShortDescription> all = auctActiveRepository.all();
        if(all!=null){
            return Response.ok(all).build();
        }
        return Response.status(400).build();
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
        return Response.status(400).build();
   }

    @GET
    @Path("/remove/{id}")
    public Response removeAuction(@PathParam("id") String id){
        AuctionsActive aa = auctActiveRepository.removeAuction(id);
        if(aa!=null){
            return Response.ok(aa).build();
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/teste/{id}")
    public Response teste(@PathParam("id") String id){
        AuctionsActive a = auctActiveRepository.findById(id);
        return Response.status(200).build();
    }


    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("activateAuction"));
            while (true) {
                Message message = consumer.receive();
                System.out.println("I got a message! " + message.toString());
                if (message == null) {
                    System.out.println("é nulo!");
                    return;
                }
                ObjectMessage om = (ObjectMessage) message;
                System.out.println("OM: " + om.toString());
                StartingInfo auction = message.getBody(StartingInfo.class);
                System.out.println("auction: " + auction.toString());
                if(auctActiveRepository.findById(auction.getAuctionId())==null){
                    AuctionsActive aa= auctActiveRepository.newAuction(auction.getAuctionId(),auction.getDuration(),auction.getStartingPrice(),auction.getReservePrice(),auction.getSellerId());
                    prepareToClose.produce(aa.getId(),aa.getEndTimeAuction());
                }
            }
        } catch (Exception e) {
            System.out.println("errou");
            e.printStackTrace();
        }
    }


}