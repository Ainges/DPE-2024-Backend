package de.thi;

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

        AnnualStatementPreparationReminderDto annualStatementPreparationReminderDto = exchange.getMessage().getBody(AnnualStatementPreparationReminderDto.class);

        String mailTempalte = "annual-statement-preparation-reminder.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTempalte)
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
        //TODO: Make Subject dynamic
        exchange.getIn().setHeader("Subject", "Eine Nachricht von equipli.de!");
        exchange.getIn().setHeader("To", "example@example.org");
        exchange.getIn().setHeader("From", "equipli <info.equipli@gmail.com>");
        exchange.getIn().setHeader("Content-Type", "text/html");
        exchange.getIn().setBody(htmlTemplate);


    }

}
