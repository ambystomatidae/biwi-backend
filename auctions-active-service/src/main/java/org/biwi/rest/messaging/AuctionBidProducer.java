package org.biwi.rest.messaging;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;


@ApplicationScoped
public class AuctionBidProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(String id, double value) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            TextMessage tm = context.createTextMessage(String.valueOf(value));
            jp.send(context.createTopic(id), tm);
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }
}
