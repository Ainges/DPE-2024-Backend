package de.thi.processor;


import de.thi.dto.QRCodePaymentDto;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Processor that sends a QR code for payment via email.
 * <p>
 * This processor is responsible for processing a QRCodePaymentDto object and sending the QR code for payment via email.
 * It uses a template to generate the email content and sets the necessary headers for the email.
 */
@ApplicationScoped
public class SendQRCodeProcessor implements Processor {

    /**
     * Injects the TemplateProducer to access the email template.
     */
    @Inject
    TemplateProducer templateProducer;

    /**
     * Processes the exchange by sending the QR code for payment via email.
     * <p>
     * This method processes the exchange by extracting the QRCodePaymentDto object from the message body.
     * It then generates the email content using a template and sets the necessary headers for the email.
     * The email content includes the receiver's name, receiver, amount, IBAN, currency, description, and the QR code image.
     * The email is sent to the landlord's email address.
     *
     * @param exchange the exchange containing the QRCodePaymentDto object
     * @throws Exception if an error occurs during processing
     */
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
