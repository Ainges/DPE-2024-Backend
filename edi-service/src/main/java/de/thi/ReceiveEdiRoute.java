package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class ReceiveEdiRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest("/edi/")
            .post("test")
            .type(String.class)
            .consumes("text/plain")
            .produces("text/plain")
            .to("direct:receiveEdi");


        from("activemq:queue:edi")
            .log("Received EDI message from ActiveMQ: ${body}")
            .to("direct:receiveEdi")
            .end();

        from("direct:receiveEdi")
            .log("Received EDI message: ${body}")
            .end();
    }
}
