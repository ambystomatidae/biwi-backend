package org.biwi.rest.messaging;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.biwi.external.StartingInfo;
import org.biwi.rest.AuctionsActiveRepository;
import org.biwi.rest.model.AuctionsActive;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class ActivateAuctionConsumer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    AuctionsActiveRepository auctActiveRepository;

    @Inject
    PrepareToCloseProducer prepareToClose;

    @Inject
    AuctionBidProducer auctionbid;


    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("activateAuction"));
            while (true) {
                Message message = consumer.receive();
                if (message == null) {
                    return;
                }
                ObjectMessage om = (ObjectMessage) message;
                StartingInfo auction = message.getBody(StartingInfo.class);
                if(auctActiveRepository.findById(auction.getAuctionId())==null){
                    AuctionsActive aa= auctActiveRepository.newAuction(auction.getAuctionId(),auction.getDuration(),auction.getStartingPrice(),auction.getReservePrice(),auction.getSellerId());
                    prepareToClose.produce(aa.getId(),aa.getEndTimeAuction());
                    auctionbid.initiate(aa.getId(), aa.getStartingPrice());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
