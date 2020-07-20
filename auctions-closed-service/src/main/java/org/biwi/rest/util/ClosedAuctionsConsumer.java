package org.biwi.rest.util;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Session;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.biwi.rest.models.ClosedAuction;
import org.biwi.rest.repositories.ClosedAuctionsRepository;
import org.eclipse.microprofile.config.ConfigProvider;

@ApplicationScoped
public class ClosedAuctionsConsumer implements Runnable {

    String queueName = ConfigProvider.getConfig().getValue("auctions-closed-queue.name", String.class);

    @Inject
    ClosedAuctionsRepository closedAuctionsRepository;

    @Inject
    ConnectionFactory connectionFactory;

    RequestsHandler requestsHandler = RequestsHandler.getInstance();

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
            JMSConsumer consumer = context.createConsumer(context.createQueue(queueName));
            while (true) {
                Message message = consumer.receive();
                if (message == null) return;
                String auctionId = message.getBody(String.class);
                ClosedAuction auction = new ClosedAuction(auctionId, requestsHandler.getAuctionDescription(auctionId), requestsHandler.getAuctionData(auctionId));
                closedAuctionsRepository.persistAuction(auction);
                requestsHandler.removeAuction(auction.id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}