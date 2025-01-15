package de.thi.processor;

import de.thi.NotificationRouteException;
import de.thi.dto.AnnualStatementNotificationDto;
import de.thi.service.AnnualStatementService;
import de.thi.service.Base64Service;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.runtime.TemplateProducer;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.File;

/**
 * Processor for sending annual statements to tenants.
 * <p>
 * This processor retrieves the annual statement data from the annual statement service,
 * processes it, and sends it to the tenant via email.
 * <p>
 *
 */
@ApplicationScoped
public class AnnualStatementProcessor implements Processor {

    /**
     * Injects the TemplateProducer to generate email templates.
     */
    @Inject
    TemplateProducer templateProducer;

    @Inject
    Base64Service base64Service;

    /**
     * Injects the AnnualStatementService to retrieve annual statement data.
     */
    @RestClient
    AnnualStatementService annualStatementService;

    /**
     * Processes the incoming exchange to send annual statements to tenants.
     * <p>
     * This method retrieves the annual statement data from the annual statement service,
     * processes it, and sends it to the tenant via email.
     *
     * @param exchange the incoming exchange containing the annual statement notification data
     * @throws Exception if an error occurs during processing
     */
    @Override
    public void process(Exchange exchange) throws Exception {

        AnnualStatementNotificationDto annualStatementNotificationDto = exchange.getMessage().getBody(AnnualStatementNotificationDto.class);

        System.out.println("Sending Annual Statement with ID: " + annualStatementNotificationDto.getData().getAnnualStatementId());


        //TODO: get real base64 encoded annual statement with http client from annual statement service
        String base64EncodedAnnualStatementWithPrefix = "data:application/pdf;base64,JVBERi0xLjQKJfbk/N8KMSAwIG9iago8PAovVHlwZSAvQ2F0YWxvZwovVmVyc2lvbiAvMS40Ci9QYWdlcyAyIDAgUgo+PgplbmRvYmoKMiAwIG9iago8PAovVHlwZSAvUGFnZXMKL0tpZHMgWzMgMCBSXQovQ291bnQgMQo+PgplbmRvYmoKMyAwIG9iago8PAovVHlwZSAvUGFnZQovTWVkaWFCb3ggWzAuMCAwLjAgNjEyLjAgNzkyLjBdCi9QYXJlbnQgMiAwIFIKL0NvbnRlbnRzIDQgMCBSCi9SZXNvdXJjZXMgNSAwIFIKPj4KZW5kb2JqCjQgMCBvYmoKPDwKL0xlbmd0aCAxNTcKL0ZpbHRlciAvRmxhdGVEZWNvZGUKPj4Kc3RyZWFtDQp4nHMK4dJ3M1QwNFMISeMyNVAwB+KQFC4Nx7y80sQcheCSxJLU3NS8Ek2FkCwuAwVdI7C0vpuRgqERSIuGS2ZxSVFmUmlJZn6egndqpZUCiJ+XDtNgaAo2LyC1KDM/BWReUQl+Ja55KTgUhOSXAJ3knF8MNMFAzwCrbEBRakFiJcjFxdgUuWSmpaUWpeYlp6LIGoN95RrCBQAywkroDQplbmRzdHJlYW0KZW5kb2JqCjUgMCBvYmoKPDwKL0ZvbnQgNiAwIFIKPj4KZW5kb2JqCjYgMCBvYmoKPDwKL0YxIDcgMCBSCi9GMiA4IDAgUgo+PgplbmRvYmoKNyAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9IZWx2ZXRpY2EtQm9sZAovRW5jb2RpbmcgL1dpbkFuc2lFbmNvZGluZwo+PgplbmRvYmoKOCAwIG9iago8PAovVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTEKL0Jhc2VGb250IC9IZWx2ZXRpY2EKL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKPj4KZW5kb2JqCnhyZWYKMCA5CjAwMDAwMDAwMDAgNjU1MzUgZg0KMDAwMDAwMDAxNSAwMDAwMCBuDQowMDAwMDAwMDc4IDAwMDAwIG4NCjAwMDAwMDAxMzUgMDAwMDAgbg0KMDAwMDAwMDI0NyAwMDAwMCBuDQowMDAwMDAwNDc4IDAwMDAwIG4NCjAwMDAwMDA1MTEgMDAwMDAgbg0KMDAwMDAwMDU1MiAwMDAwMCBuDQowMDAwMDAwNjU0IDAwMDAwIG4NCnRyYWlsZXIKPDwKL1Jvb3QgMSAwIFIKL0lEIFs8NTUwMTFEREFENTE5QkFDNTUzOTU2MjkxOTgzNkZGOUY+IDw1NTAxMUREQUQ1MTlCQUM1NTM5NTYyOTE5ODM2RkY5Rj5dCi9TaXplIDkKPj4Kc3RhcnR4cmVmCjc1MQolJUVPRgo=";

        try {

            base64EncodedAnnualStatementWithPrefix = annualStatementService.getBase64String(annualStatementNotificationDto.getData().getAnnualStatementId());

            if (base64EncodedAnnualStatementWithPrefix.startsWith("AnnualStatement with ID 1 not found.")) {
                throw new NotificationRouteException("Could not get Annual Statement with ID: " + annualStatementNotificationDto.getData().getAnnualStatementId());
            }

        } catch (Exception e) {
            throw new NotificationRouteException("Could not get Annual Statement with ID: " + annualStatementNotificationDto.getData().getAnnualStatementId());
        }

        /**
         * Convert base64 encoded annual statement to PDF file
         */
        String filePath = base64Service.convertBase64ToAnnualStatement(base64EncodedAnnualStatementWithPrefix, annualStatementNotificationDto);

        /**
         * Get filename from filePath
         */
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);

        /**
         * Attach Annual Statement to Mail
          */
        File file = new File(filePath);
        DataSource dataSource = new FileDataSource(file);
        AttachmentMessage attMsg = exchange.getIn(AttachmentMessage.class);
        attMsg.addAttachment(filename, new DataHandler(dataSource));

        /**
         * tenant paid exactly
         */
        switch (annualStatementNotificationDto.getMailType()) {
            case "annualStatement" -> {

                String mailTemplate = "AS-tenant-payed-exactly";

                TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                        .data("receiver_name", "Max Mustermann");

                String htmlTemplate = templateInstance.render();

                /**
                 * Create mail
                 */
                exchange.getIn().setBody(htmlTemplate);
            }


            /**
             * Tenant paid too much
             */
            case "annualStatementPaymentInformationRequest" -> {

                String mailTemplate = "AS-tenant-payed-to-much";

                TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                        .data("receiver_name", "Max Mustermann")
                        // get absolute value of difference
                        // print difference with two decimal places
                        .data("amount", String.format("%.2f", Math.abs(annualStatementNotificationDto.getData().getDifference())) + "€");

                String htmlTemplate = templateInstance.render();

                /**
                 * Create mail
                 */
                exchange.getIn().setBody(htmlTemplate);

            }


            /**
             * Tenant paid less
             */
            case "annualStatementPaymentInformation" -> {

                String mailTemplate = "AS-tenant-payed-less";

                TemplateInstance templateInstance = templateProducer.getInjectableTemplate(mailTemplate)
                        .data("receiver_name", "Max Mustermann")
                        // get absolute value of difference
                        .data("amount", String.format("%.2f", Math.abs(annualStatementNotificationDto.getData().getDifference())) + "€");

                String htmlTemplate = templateInstance.render();

                /**
                 * Create mail
                 */
                exchange.getIn().setBody(htmlTemplate);

            }
            case null, default ->
                    throw new NotificationRouteException("MailType not supported: " + annualStatementNotificationDto.getMailType());
        }

        /**
         * Set mail headers
         */
        exchange.getIn().setHeader("Subject", "Ihre Jahresabrechnung");
        exchange.getIn().setHeader("To", "tenant@dpe-2024.de");
        exchange.getIn().setHeader("From", "Hausverwaltung <info.dpe2024>");


    }
}
