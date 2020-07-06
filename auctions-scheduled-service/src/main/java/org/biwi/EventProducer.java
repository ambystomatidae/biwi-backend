package org.biwi;

import org.biwi.external.StartingInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequestScoped
public class EventProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(StartingInfo msg, LocalDateTime start) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            ObjectMessage om = context.createObjectMessage(msg);
            om.setLongProperty("_AMQ_SCHED_DELIVERY", getDeliveryDelay(start));
            jp.send(context.createQueue("activateAuction"), om);

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
        ZonedDateTime zdt = start.atZone(ZoneId.of("GMT"));
        long startTime = zdt.toInstant().toEpochMilli();
        return startTime - System.currentTimeMillis();
    }
}
