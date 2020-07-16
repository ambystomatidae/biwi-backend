package org.biwi.rest.messaging;

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


    public void produce(String id, LocalDateTime end) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            ObjectMessage om = context.createObjectMessage(id);
            om.setLongProperty("_AMQ_SCHED_DELIVERY", getDeliveryDelay(end));
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
    private static long getDeliveryDelay(LocalDateTime end) {
        ZonedDateTime zdt = end.atZone(ZoneId.of("Europe/London"));
        return zdt.toInstant().toEpochMilli();
    }



}
