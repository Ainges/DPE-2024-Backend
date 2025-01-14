package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

@ApplicationScoped
public class ReceiveEdiRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {


        // ## Ingress
        rest("/edi/")
            .post("payment-received")
            .type(String.class)
            .consumes("text/plain")
            .produces("text/plain")
            .to("direct:payment-received");


        // Currently not used, but could be used to used to convert EDI messages to JSON and XML
//        from("activemq:queue:ediJsonToXML")
//            .log("Received EDI message from ActiveMQ: ${body}")
//            .unmarshal().json()
//            .marshal().jacksonXml()
//            .to("file:target/output?fileName=edi.xml")
//            .log("Saved it as edi.xml")
//            .end();
//
//        // Conversion without conversion to Java object, because without a explicit class, the conversion is inaccurate -> root element is missing
//        from("activemq:queue:ediXMLToJson")
//            .log("Received EDI message from ActiveMQ: ${body}")
//            .unmarshal().jacksonXml()
//            .marshal().json()
//            .to("file:target/output?fileName=edi.json")
//            .log("Saved it as edi.json")
//            .end();


        // ## Egress

        from("direct:payment-received")
            .routeId("Receive-Payment-Route")
            .log("Received Payment! Informing ProcessEngine...")
            .setBody(simple("Payment received!"))
            .marshal().json(JsonLibrary.Jackson)
                // Remove all headers to avoid problems with the following HTTP request
                .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader("Spiffworkflow-Api-Key", simple("ee48a2bd-6825-4baf-ac70-536a11ba0022"))
            //TODO: Change back to external-invoice-received | only done to not have to deal with the absurdly long delay of the ProcessEngine
            .to("http://localhost:8000/v1.0/messages/external-monthly-rent-received")
            .log("Successfully informed ProcessEngine!");

        // Inform ProcessEngine about received Invoice
        from("activemq:queue:external-invoice-received")
                .routeId("receive-Invoice-Route")
                .log("Received invoice from ActiveMQ: ${body}")
                .unmarshal().jacksonXml(DocumentDto.class)
                .process(exchange -> {
                    DocumentDto documentDTO = exchange.getMessage().getBody(DocumentDto.class);
                    if(documentDTO.getDigitalInvoice()) {
                        System.out.println("Received digital invoice with receiver: " + documentDTO.getInvoice().getReceiver());
                    } else {
                        System.out.println("Received manual invoice");
                    }
                    exchange.getMessage().setBody(documentDTO);
                })
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Spiffworkflow-Api-Key", simple("ee48a2bd-6825-4baf-ac70-536a11ba0022"))
                //TODO: Change back to external-invoice-received | only done to not have to deal with the absurdly long delay of the ProcessEngine
                .to("http://localhost:8000/v1.0/messages/invoice-received")
                .log("Sent invoice to ProcessEngine!");


    }
}
