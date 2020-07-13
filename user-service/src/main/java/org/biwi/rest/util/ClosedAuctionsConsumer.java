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
import org.biwi.rest.models.Auction;
import org.biwi.rest.repositories.UserRepository;
import org.eclipse.microprofile.config.ConfigProvider;

@ApplicationScoped
public class ClosedAuctionsConsumer implements Runnable {

    String queueName = ConfigProvider.getConfig().getValue("auctions-closed-queue.name", String.class);

    @Inject
    UserRepository userRepository;

    @Inject
    ConnectionFactory connectionFactory;

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
            while (true){
                Message message = consumer.receive();
                if (message == null) return ;
                userRepository.removeFromWatchlist(new Auction(message.getBody(String.class)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}