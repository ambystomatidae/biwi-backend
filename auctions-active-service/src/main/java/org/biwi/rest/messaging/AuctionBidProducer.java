package org.biwi.rest.messaging;
import org.biwi.rest.model.Bid;
import org.biwi.rest.model.BidEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;


@ApplicationScoped
public class AuctionBidProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(Bid bid, String id) {
        try {
            BidEvent be= new BidEvent(id, bid.getValue(), bid.getTimeStamp(), bid.getIdUser());
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            ObjectMessage om = context.createObjectMessage(be);
            jp.send(context.createQueue("auctionBid"), om);
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }
}
