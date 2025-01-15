package de.thi.processor;

import de.thi.dto.AnnualStatementPreparationReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Processor for preparing the annual statement reminder email.
 * <p>
 * This processor implements the Processor interface from Apache Camel and is annotated with @ApplicationScoped, indicating it is a CDI (Contexts and Dependency Injection) bean with application scope.
 * The process method is overridden to define the processing logic for the annual statement reminder email.
 * The processor uses the TemplateProducer to generate the email content based on a template.
 * The email content is then set as the body of the exchange, and the email headers are set accordingly.
 */
@ApplicationScoped
public class AnnualStatementPreparationReminderProcessor implements Processor {

    /**
     * Injects the TemplateProducer to generate the email content based on a template.
     */
    @Inject
    TemplateProducer templateProducer;

    /**
     * Processes the annual statement preparation reminder email.
     * <p>
     * This method is called by Apache Camel to process the annual statement preparation reminder email.
     * It retrieves the annual statement preparation reminder details from the exchange message body.
     * It generates the email content based on a template using the TemplateProducer.
     * It sets the email content as the body of the exchange and sets the email headers accordingly.
     *
     * @param exchange the Apache Camel exchange containing the annual statement preparation reminder details
     * @throws Exception if an error occurs during processing
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        /**
         *  Indeed Duplicate to DelayedAnnualStatementReminderProcessor. But it remains here to be flexible for future changes.
          */
        AnnualStatementPreparationReminderDto annualStatementPreparationReminderDto = exchange.getMessage().getBody(AnnualStatementPreparationReminderDto.class);

        String mailTemplate = "annual-statement-preparation-reminder.html";

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

        /**
         * create mail
          */
        exchange.getIn().setBody(htmlTemplate);

        exchange.getIn().setHeader("Subject", "Erinnerung: Vorbereitung Jahresabrechnung");
        exchange.getIn().setHeader("To", "landlord@dpe-2024.de");


    }

}
