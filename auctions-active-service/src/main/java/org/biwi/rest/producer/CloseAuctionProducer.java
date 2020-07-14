package org.biwi.rest.producer;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;


@RequestScoped
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