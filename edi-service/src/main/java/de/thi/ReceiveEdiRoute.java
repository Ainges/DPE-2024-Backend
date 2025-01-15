package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * defines a REST endpoint using Apache Camel.
 * The ReceiveEdiRoute class extends RouteBuilder and is annotated with @ApplicationScoped, indicating it is a CDI (Contexts and Dependency Injection) bean with application scope.
 * The configure method is overridden to define the routing logic.
 * A REST endpoint is defined at /edi/payment-received:
 * It accepts POST requests.
 * The request and response body types are String.
 * It consumes and produces text/plain content type.
 * The request is routed to the direct:payment-received endpoint for further processing
 */
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


        /**
         * defines a route in Apache Camel that processes incoming messages from the direct:payment-received endpoint. Here is a step-by-step explanation of what happens:
         * Route Definition: The route starts from the direct:payment-received endpoint.
         * Route ID: The route is assigned an ID: Receive-Payment-Route.
         * Logging: Logs the message "Received Payment! Informing ProcessEngine...".
         * Set Body: Sets the body of the message to the string "Payment received!".
         * Marshal to JSON: Converts the message body to JSON format using Jackson.
         * Remove Headers: Removes all headers from the message to avoid issues with the subsequent HTTP request.
         * Set HTTP Headers: Sets the HTTP method to POST.
         * Sets the Content-Type header to application/json.
         * Sets a custom header Spiffworkflow-Api-Key with a specific API key.
         * Send HTTP Request: Sends the message to the URL http://localhost:8000/v1.0/messages/external-monthly-rent-received.
         * Logging:Logs the message "Successfully informed ProcessEngine!".
         */
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

        /**
         * Inform ProcessEngine about received Invoice
         * defines a route in Apache Camel that processes incoming messages from the activemq:queue:external-invoice-received queue.
         * Route Definition: The route starts from the activemq:queue:external-invoice-received endpoint.
         * Route ID: The route is assigned an ID: receive-Invoice-Route.
         * Logging: Logs the message "Received invoice from ActiveMQ: ${body}".
         * Unmarshal: Converts the message body from XML to a DocumentDto object using Jackson.
         * Processing: Processes the DocumentDto object:
         * Checks if the invoice is digital.
         * Logs the receiver of the digital invoice or indicates a manual invoice.
         * Sets the processed DocumentDto object back to the message body.
         * Marshal: Converts the message body to JSON format using Jackson.
         * Set HTTP Headers: Sets the HTTP method to POST and the Content-Type header to application/json.
         * Custom Header: Sets a custom header Spiffworkflow-Api-Key with a specific API key.
         * Send HTTP Request: Sends the message to the URL http://localhost:8000/v1.0/messages/invoice-received.
         * Logging: Logs the message "Sent invoice to ProcessEngine!".
         */
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
