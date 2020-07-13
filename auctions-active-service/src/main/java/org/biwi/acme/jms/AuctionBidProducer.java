package org.biwi.acme.jms;

import org.biwi.rest.BidEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Session;



@ApplicationScoped
public class AuctionBidProducer {

    @Inject
    ConnectionFactory connectionFactory;

    public void produce(BidEvent bid) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            ObjectMessage om = context.createObjectMessage(bid);
            context.createProducer().send(context.createQueue("auctionBid"), om );
        }
    }
}