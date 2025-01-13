package de.thi.processor;


import de.thi.dto.SendPaymentReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class SendPaymentReminderProcessor implements Processor {
    private final TemplateProducer templateProducer;

    @Inject
    public SendPaymentReminderProcessor(TemplateProducer templateProducer) {
        this.templateProducer = templateProducer;
    }

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




    }
}
