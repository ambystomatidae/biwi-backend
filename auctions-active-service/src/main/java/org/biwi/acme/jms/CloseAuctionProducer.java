package org.biwi.acme.jms;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * A bean producing random prices every 5 seconds and sending them to the prices JMS queue.
 */
@ApplicationScoped
public class CloseAuctionProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(String id) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            context.createProducer().send(context.createQueue("closeAuction"), id );
        }
    }
}