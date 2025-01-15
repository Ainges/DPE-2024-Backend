package de.thi.processor;


import de.thi.dto.QRCodePaymentDto;
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

        QRCodePaymentDto qrCodePaymentDTO = exchange.getMessage().getBody(QRCodePaymentDto.class);

        String mailTemplate = "send-qrcode.html";

        TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                .data("receiver_name", "Max Mustermann")
                .data("receiver", qrCodePaymentDTO.getData().getObjPaymentDetails().getReceiver())
                .data("amount", qrCodePaymentDTO.getData().getObjPaymentDetails().getInvoiceAmount())
                .data("iban", qrCodePaymentDTO.getData().getObjPaymentDetails().getReceiverIban())
                .data("currency", qrCodePaymentDTO.getData().getObjPaymentDetails().getCurrency())
                .data("description", qrCodePaymentDTO.getData().getObjPaymentDetails().getDescription())
                .data("qrcode", qrCodePaymentDTO.getData().getBase64QR())

                ;

        String htmlTemplate = templateInstance.render();

        exchange.getIn().setBody(htmlTemplate);

        exchange.getIn().setHeader("Subject", "QR Code for Payment");
        exchange.getIn().setHeader("To", "landlord@dpe-2024.de");


    }
}
