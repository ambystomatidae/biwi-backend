package org.biwi;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class Consumer implements Runnable{

    @Inject
    ConnectionFactory connectionFactory;

    private volatile String lastMsg;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    public String getLastMsg() {
        return this.lastMsg;
    }

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSConsumer consumer = context.createConsumer(context.createQueue("awesomequeue"));
            while(true) {
                Message msg = consumer.receive();
                if (msg == null) return;
                lastMsg = msg.getBody(String.class);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
