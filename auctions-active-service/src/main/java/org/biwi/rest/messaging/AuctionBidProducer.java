package org.biwi.rest.messaging;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;


@ApplicationScoped
public class AuctionBidProducer {

    @Inject
    ConnectionFactory connectionFactory;

    private Topic topic;



    public void initiate(String id, double value ) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            this.topic= context.createTopic(id);
            JMSProducer jp = context.createProducer();
            TextMessage tm = context.createTextMessage(String.valueOf(value));
            jp.send(this.topic, tm);

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void produce(String id, double value){
        JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
        JMSProducer jp = context.createProducer();
        TextMessage tm = context.createTextMessage(String.valueOf(value));
        jp.send(this.topic, tm);
    }
}
