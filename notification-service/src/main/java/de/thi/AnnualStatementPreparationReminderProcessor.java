package de.thi;

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

        String mailTempalte = "annual-statement-preparation-reminder.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTempalte)
                .data("name", "Max Mustermann");

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
