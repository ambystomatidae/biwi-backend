package org.biwi;

import org.biwi.models.ScheduleAuctionEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class EventProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(ScheduleAuctionEvent msg, LocalDateTime start) {
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

    private static long getDeliveryDelay(LocalDateTime start) {
        ZonedDateTime zdt = start.atZone(ZoneId.of("GMT"));
        long startTime = zdt.toInstant().toEpochMilli();
        return startTime - System.currentTimeMillis();
    }
}
