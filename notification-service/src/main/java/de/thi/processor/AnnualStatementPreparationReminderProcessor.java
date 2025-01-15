package de.thi.processor;

import de.thi.dto.AnnualStatementPreparationReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class AnnualStatementPreparationReminderProcessor implements Processor {

    @Inject
    TemplateProducer templateProducer;

    @Override
    public void process(Exchange exchange) throws Exception {

        // Indeed Duplicate to DelayedAnnualStatementReminderProcessor. But it remains here to be flexible for future changes.
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

        // create mail
        exchange.getIn().setBody(htmlTemplate);


    }

}
