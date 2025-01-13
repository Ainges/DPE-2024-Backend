package de.thi.processor;


import de.thi.dto.QRCodePaymentDTO;
import de.thi.dto.SendPaymentReminderDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class SendQRCodeProcessor implements Processor {

    @Inject
    TemplateProducer templateProducer;

    @Override
    public void process(Exchange exchange) throws Exception {

        QRCodePaymentDTO qrCodePaymentDTO = exchange.getMessage().getBody(QRCodePaymentDTO.class);

        System.out.println("Sending QR Code to: " + qrCodePaymentDTO.toString());

        String mailTemplate = "send-qrcode.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                .data("receiver_name", "Max Mustermann")
                .data("qrcode", qrCodePaymentDTO.getData().getBase64QR())
                ;

        String htmlTemplate = templateInstance.render();

        exchange.getIn().setBody(htmlTemplate);

        exchange.getIn().setHeader("Subject", "QR Code for Payment");
        exchange.getIn().setHeader("To", "landlord@dpe-2024.de");


    }
}
