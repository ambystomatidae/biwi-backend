package org.biwi.rest.producer;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@ApplicationScoped
public class CloseAuctionProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(String id, LocalDateTime start) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            ObjectMessage om = context.createObjectMessage(id);
            long delay = getDeliveryDelay(start);
            om.setLongProperty("_AMQ_SCHED_DELIVERY", delay);
            jp.send(context.createQueue("closeAuction"), om);
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * @param start when the message is supposed to be delivered
     * @return millis until the message is supposed to be delivered
     */
    private static long getDeliveryDelay(LocalDateTime start) {
        ZonedDateTime zdt = start.atZone(ZoneId.of("Europe/London"));
        long startTime = zdt.toInstant().toEpochMilli();
        return startTime - System.currentTimeMillis();
    }
}