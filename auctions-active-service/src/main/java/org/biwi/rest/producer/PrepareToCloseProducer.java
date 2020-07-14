package org.biwi.rest.producer;

import org.biwi.rest.model.Bid;
import org.biwi.rest.model.BidEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class PrepareToCloseProducer {

    @Inject
    ConnectionFactory connectionFactory;


    public void produce(String id, LocalDateTime start) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            ObjectMessage om = context.createObjectMessage(id);
            om.setLongProperty("_AMQ_SCHED_DELIVERY", getDeliveryDelay(start));
            jp.send(context.createQueue("prepareToClose"), om);
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
        ZonedDateTime zdt = start.atZone(ZoneId.of("UTC"));
        return zdt.toInstant().toEpochMilli();
    }



}
