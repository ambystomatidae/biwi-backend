package org.biwi.rest.messaging;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.biwi.rest.AuctionsActiveRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@ApplicationScoped
public class PrepareToCloseConsumer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    AuctionsActiveRepository aaRepository;

    @Inject
    CloseAuctionProducer closeActionProducer;


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
            JMSConsumer consumer = context.createConsumer(context.createQueue("prepareToClose"));
            while (true) {
                Message message = consumer.receive();
                if (message == null) return;
                String id = message.getBody(String.class);
                closeActionProducer.produce(id);
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

