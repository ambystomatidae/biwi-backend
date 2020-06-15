package org.biwi;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Producer {

    @Inject
    ConnectionFactory connectionFactory;

    @POST
    public Response produce(String msg) {
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSProducer send = context.createProducer().send(context.createQueue("awesomequeue"), msg);
            return Response.ok(msg).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
