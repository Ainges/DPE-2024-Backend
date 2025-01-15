package de.thi.processor;


import de.thi.dto.SendPaymentReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Processor class that sends a payment reminder email.
 * <p>
 * This class implements the Processor interface from Apache Camel.
 * The process method is overridden to define the processing logic.
 * The SendPaymentReminderProcessor class is annotated with @ApplicationScoped, indicating it is a CDI (Contexts and Dependency Injection) bean with application scope.
 * The TemplateProducer is injected into the class using the @Inject annotation.
 * The process method retrieves the SendPaymentReminderDto from the exchange message body.
 * It then generates an HTML email template using the Quarkus TemplateProducer.
 * The template is populated with data from the SendPaymentReminderDto.
 * The email subject, recipient, and HTML content are set in the exchange message headers and body.
 */
@ApplicationScoped
public class SendPaymentReminderProcessor implements Processor {
    private final TemplateProducer templateProducer;

    /**
     * Constructor for the SendPaymentReminderProcessor class.
     * The TemplateProducer is injected into the class using the @Inject annotation.
     * @param templateProducer The TemplateProducer instance to generate email templates.
     */
    @Inject
    public SendPaymentReminderProcessor(TemplateProducer templateProducer) {
        this.templateProducer = templateProducer;
    }

    /**
     * Processes the exchange message to send a payment reminder email.
     * The process method is called by Apache Camel to process the exchange message.
     * It retrieves the SendPaymentReminderDto from the exchange message body.
     * Generates an HTML email template using the Quarkus TemplateProducer.
     * Populates the template with data from the SendPaymentReminderDto.
     * Sets the email subject, recipient, and HTML content in the exchange message headers and body.
     * @param exchange The exchange containing the message to be processed.
     * @throws Exception if an error occurs during processing.
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        SendPaymentReminderDto sendPaymentReminderDto = exchange.getMessage().getBody(SendPaymentReminderDto.class);

        String mailTemplate = "send-payment-reminder.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                .data("receiver_name", "Max Mustermann")
                .data("receiver", sendPaymentReminderDto.getData().getReceiver())
                .data("receiverIban", sendPaymentReminderDto.getData().getReceiverIban())
                .data("invoiceAmount",sendPaymentReminderDto.getData().getInvoiceAmount())
                ;

        String htmlTemplate = templateInstance.render();

        exchange.getIn().setBody(htmlTemplate);

        exchange.getIn().setHeader("Subject", "Ausstehende Zahlung");
        exchange.getIn().setHeader("To", "landlord@dpe-2024.de");

    }
}
