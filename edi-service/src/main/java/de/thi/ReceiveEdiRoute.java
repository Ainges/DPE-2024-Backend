package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

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

        from("activemq:queue:ediJsonToXML")
            .log("Received EDI message from ActiveMQ: ${body}")
            .unmarshal().json()
            .marshal().jacksonXml()
            .to("file:target/output?fileName=edi.xml")
            .log("Saved it as edi.xml")
            .end();

        // Conversion without conversion to Java object, because without a explicit class, the conversion is inaccurate -> root element is missing
        from("activemq:queue:ediXMLToJson")
            .log("Received EDI message from ActiveMQ: ${body}")
            .unmarshal().jacksonXml()
            .marshal().json()
            .to("file:target/output?fileName=edi.json")
            .log("Saved it as edi.json")
            .end();


        // Inform ProcessEngine about received Invoice
        from("activemq:queue:invoice_received")
                .routeId("receive-Invoice-Route")
                .log("Received invoice from ActiveMQ: ${body}")
                .unmarshal().jacksonXml(NewInvoiceReceivedDto.class)
                .process(exchange -> {
                    NewInvoiceReceivedDto invoice = exchange.getMessage().getBody(NewInvoiceReceivedDto.class);
                    exchange.getMessage().setBody(invoice);
                })
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Spiffworkflow-Api-Key", simple("ee48a2bd-6825-4baf-ac70-536a11ba0022"))
                .to("http://localhost:8000/v1.0/messages/invoice_received");

        // Send digital invoice to ProcessEngine
        from("activemq:queue:digital-invoice-received")
                .routeId("receiveEdi-Route")
                .log("Received EDI message from ActiveMQ: ${body}")
                .to("file:target/output/receivedInvoices/?fileName=invoice.xml")
                .log("Original invoice saved as: 'invoice.xml'")
                .unmarshal().jacksonXml(InvoiceCreateDto.class)
                .process(exchange -> {
                            InvoiceCreateDto invoice = exchange.getMessage().getBody(InvoiceCreateDto.class);
                            System.out.println("Received invoice with receiver: " + invoice.getReceiver());
                            exchange.getMessage().setBody(invoice);
                        }
                )
                .marshal().json(JsonLibrary.Jackson)
                .log("Converted invoice to JSON: ${body}")
                .log("Sending Invoice as JSON to ProcessEngine...")
                // Prepare for sending to ProcessEngine
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Spiffworkflow-Api-Key", simple("ee48a2bd-6825-4baf-ac70-536a11ba0022"))
                .to("http://localhost:8000/v1.0/messages/receive_digital_invoice");

    }
}
