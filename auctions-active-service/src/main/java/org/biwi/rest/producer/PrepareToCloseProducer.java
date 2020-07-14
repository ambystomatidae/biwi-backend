package org.biwi.rest.producer;

import org.biwi.rest.model.Bid;
import org.biwi.rest.model.BidEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import javax.jms.*;

public class PrepareToCloseProducer {

    @Inject
    ConnectionFactory connectionFactory;


    public void produce(String id) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer jp = context.createProducer();
            jp.send(context.createQueue("auctionBid"), id);
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

}
