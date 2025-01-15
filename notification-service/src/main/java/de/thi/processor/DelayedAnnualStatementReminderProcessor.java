package de.thi.processor;

import de.thi.dto.AnnualStatementPreparationReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Processor for sending a delayed annual statement reminder to the landlord.
 * <p>
 * This processor implements the Processor interface from Apache Camel and is annotated with @ApplicationScoped, indicating it is a CDI (Contexts and Dependency Injection) bean with application scope.
 * The process method is overridden to define the processing logic for the delayed annual statement reminder.
 * The delayed annual statement reminder is sent to the landlord when the annual statement preparation is delayed.
 * The processor retrieves the data from the exchange message body, generates an HTML email template using Qute, and sets the email subject and recipient address in the exchange headers.
 */
@ApplicationScoped
public class DelayedAnnualStatementReminderProcessor implements Processor {

    /**
     * Injects the TemplateProducer to generate email templates using Qute.
     */
    @Inject
    TemplateProducer templateProducer;

    /**
     * Processes the delayed annual statement reminder.
     * <p>
     * The process method is called by Apache Camel to process the exchange.
     * The method retrieves the annual statement preparation reminder data from the exchange message body.
     * It generates an HTML email template using Qute and sets the email subject and recipient address in the exchange headers.
     *
     * @param exchange the Apache Camel exchange containing the annual statement preparation reminder data
     * @throws Exception if an error occurs during processing
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        /**
         * Indeed Duplicate to AnnualStatementPreparationReminderProcessor. But it remains here to be flexible for future changes.
          */
        AnnualStatementPreparationReminderDto annualStatementPreparationReminderDto = exchange.getMessage().getBody(AnnualStatementPreparationReminderDto.class);

        String mailTemplate = "delayed-annual-statement-reminder.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                .data("receiver_name", "Max Mustermann")
                .data("name", annualStatementPreparationReminderDto.getData().getName())
                .data("street", annualStatementPreparationReminderDto.getData().getStreet())
                .data("city", annualStatementPreparationReminderDto.getData().getCity())
                .data("state", annualStatementPreparationReminderDto.getData().getState())
                .data("zipCode", annualStatementPreparationReminderDto.getData().getZipCode())
                .data("numberOfApartments", annualStatementPreparationReminderDto.getData().getNumberOfApartments())
                ;

        String htmlTemplate = templateInstance.render();

        exchange.getIn().setBody(htmlTemplate);

        exchange.getIn().setHeader("Subject", "Verzögerung bei Erstellung der Jahresabrechnung");
        exchange.getIn().setHeader("To", "landlord@dpe-2024.de");

    }

}
