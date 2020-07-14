package org.biwi.rest.messaging;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;


@ApplicationScoped
public class CloseAuctionProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(String id) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            jp.send(context.createQueue("closeAuction"), id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}