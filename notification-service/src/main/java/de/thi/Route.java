package de.thi;

import de.thi.dto.*;
import de.thi.processor.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * This class defines the routes for the notification service.
 * It extends the RouteBuilder class to configure the routes.
 * 
 * Configuration properties:
 * - quarkus.profile: The active profile (dev or prod).
 * - smtp.config.host: The SMTP host (default: localhost).
 * - smtp.config.port: The SMTP port (default: 2525).
 * - smtp.config.username: The SMTP username (default: user).
 * - smtp.config.password: The SMTP password (default: password).
 * 
 * Routes:
 * - POST /api/notification: Receives notification requests and routes them to "direct:notificationSort".
 * - activemq:queue:notification: Receives notifications from the ActiveMQ queue and routes them to "direct:notificationSort".
 * - direct:notificationSort: Sorts notifications based on the mailType field and routes them to the appropriate route.
 *   - type1: Routes to "direct:route1".
 *   - type2: Routes to "direct:route2".
 *   - Unsupported types: Routes to "activemq:queue:deadLetterQueue".
 * - direct:route1: Handles request confirmation notifications and routes them to "direct:sendMail".
 * - direct:route2: Handles reservation confirmation notifications and routes them to "direct:sendMail".
 * - direct:sendMail: Sends emails via SMTP or SMTPS based on the active profile.
 *   - dev profile: Sends emails via SMTP to a mocked mail server.
 *   - prod profile: Sends emails via SMTPS to a real mail server.
 */
@ApplicationScoped
public class Route extends RouteBuilder {

    @Inject
    @ConfigProperty(name = "quarkus.profile")
    String activeProfile;

    @ConfigProperty(name = "smtp.config.host", defaultValue = "localhost")
    String smtpHost;

    @ConfigProperty(name = "smtp.config.port", defaultValue = "2525")
    String smtpPort;

    @ConfigProperty(name = "smtp.config.username", defaultValue = "user")
    String username;

    @ConfigProperty(name = "smtp.config.password", defaultValue = "password")
    String password;

    @Inject
    AnnualStatementPreparationReminderProcessor annualStatementPreparationReminderProcessor;

    @Inject
    DelayedAnnualStatementReminderProcessor delayedAnnualStatementReminderProcessor;

    @Inject
    SendPaymentReminderProcessor sendPaymentReminderProcessor;

    @Inject
    SendQRCodeProcessor sendQRCodeProcessor;

    @Inject
    AnnualStatementProcessor annualStatementProcessor;


    @Override
    public void configure() throws Exception {

        // ### Ingress Routes ###

        // Message from the REST API
        rest("/api/")
                .post("notification")
                .type(NotificationDto.class)
                .consumes("application/json")
                .produces("application/json")
                .to("direct:notificationSort");


        // Message from the ActiveMQ queue
        from("activemq:queue:notification")
                .routeId("notification-from-Queue-de.thi.Route")
                .log("Notification received")
                .to("direct:notification");


        // ### -------------- ###
        // ### Internal Routes ###

        // de.thi.Route for sorting the notification
        from("direct:notification")
                .choice()
                //
                    .when(jsonpath("$.mailType").isEqualTo("annualStatementPreparationReminder")).to("direct:annualStatementPreparationReminder")
                    .when(jsonpath("$.mailType").isEqualTo("delayedAnnualStatement")).to("direct:delayedAnnualStatement")
                    .when(jsonpath("$.mailType").isEqualTo("sendPaymentReminder")).to("direct:sendPaymentReminder")
                    .when(jsonpath("$.mailType").isEqualTo("sendQRCode")).to("direct:sendQRCode")

                    // Tenant paid too much and gets a refund
                    .when(jsonpath("$.mailType").isEqualTo("annualStatementPaymentInformationRequest")).to("direct:annualStatementPaymentInformationRequest")

                    // Tenant paid less than actually due
                    .when(jsonpath("$.mailType").isEqualTo("annualStatementPaymentInformation")).to("direct:annualStatementPaymentInformation")

                    // Tenant paid exactly the amount due
                    .when(jsonpath("$.mailType").isEqualTo("annualStatement")).to("direct:annualStatement")



                //Send to DLQ (Dead-Letter-Queue) if the notification type is not supported
                    .otherwise().to("activemq:queue:deadLetterQueue");


        from("direct:annualStatementPreparationReminder")
                .routeId("annualStatementPreparationReminder-Route")
                // set the received body as a property to access it later
                .setProperty("receivedBody", body())
                // write json body to xml in fs
                .to("direct:print-raw-notification-to-xml")
                // Craft the mail
                .setBody(exchangeProperty("receivedBody"))
                // Set the mail subject
                .setHeader("Subject", simple("Erinnerung zur Erstellung der Jahresabrechnung"))
                .setHeader("To", simple("landlord@dpe2024.de"))
                .unmarshal().json(JsonLibrary.Jackson, AnnualStatementPreparationReminderDto.class)
                .process(annualStatementPreparationReminderProcessor)
                .to("direct:sendMail");

        from("direct:delayedAnnualStatement")
                .routeId("delayedAnnualStatement-Route")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .setHeader("Subject", simple("Verzögerung bei der Erstellung der Jahresabrechnung"))
                .setHeader("To", simple("landlord@dpe2024.de"))
                .unmarshal().json(JsonLibrary.Jackson, DelayedAnnualStatementReminderDto.class)
                .process(delayedAnnualStatementReminderProcessor)
                .to("direct:sendMail");

        from("direct:sendPaymentReminder")
                .routeId("sendPaymentReminder-Route")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .setHeader("Subject", simple("Erinnerung an ausstehende Zahlung"))
                .unmarshal().json(JsonLibrary.Jackson, SendPaymentReminderDto.class)
                .process(sendPaymentReminderProcessor)
                .to("direct:sendMail");

        from("direct:sendQRCode")
                .routeId("sendQRCode-Route")
                .log("sendQRCode")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .unmarshal().json(JsonLibrary.Jackson, QRCodePaymentDto.class)
                .process(sendQRCodeProcessor)
                .to("direct:sendMail");


        from("direct:annualStatementPaymentInformationRequest")
                .routeId("annualStatementPaymentInformationRequest-Route")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .unmarshal().json(JsonLibrary.Jackson, AnnualStatementNotificationDto.class)
                .process(annualStatementProcessor)
                .to("direct:sendMail");

        from("direct:annualStatementPaymentInformation")
                .routeId("annualStatementPaymentInformation-Route")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .unmarshal().json(JsonLibrary.Jackson, AnnualStatementNotificationDto.class)
                .process(annualStatementProcessor)
                .to("direct:sendMail");

        from("direct:annualStatement")
                .routeId("annualStatement-Route")
                .log("sendQRCode")
                .setProperty("receivedBody", body())
                .to("direct:print-raw-notification-to-xml")
                .setBody(exchangeProperty("receivedBody"))
                .unmarshal().json(JsonLibrary.Jackson, AnnualStatementNotificationDto.class)
                .process(annualStatementProcessor)
                .to("direct:sendMail");

        from("direct:print-raw-notification-to-xml")
                .routeId("toxml-de.thi.Route")
                .unmarshal().json(JsonLibrary.Jackson)
                // Convert the JSON body to XML
                .marshal().jacksonXml()
                // get the mailType from the xml body and set it as a header | analog to the jsonpath in the choice
                .setHeader("mailType", xpath("/LinkedHashMap/mailType/text()"))
                .log("mailType: ${header.mailType}")
                .log("printing raw notification to xml: ${body}")
                // toD: dynamic to;
                // Needed to set the file directory dynamically, would not be necessary if the file directory is static, the file name could be dynamic with .to()
                .toD("file:target/output/notifications/${header.mailType}?fileName=notification-${date:now:dd-MM-yyyy--HH-mm-ss}.xml");

        // ### -------------- ###
        // ### Egress Routes ###

        //for sending the mail
        from("direct:sendMail")
                //TODO: Remove this part after testing
                .process(exchange -> {

                    if(exchange.getIn().getHeader("Subject") == null) {
                        throw new NotificationRouteException("Could not send mail. Subject is missing.");
                    }

                    // Set the remaining mail headers to static values for this demo
                    if(exchange.getIn().getHeader("From") == null) {
                        exchange.getIn().setHeader("From", "info.dpe2024@gmail.com");

                    }
                    exchange.getIn().setHeader("Content-Type", "text/html");
                })
                .choice()

                // Send the mail via SMTP based on the active profile (dev or prod)
                // If the active profile is dev, the mail is sent via SMTP to a mocked mail server inside a docker container, otherwise via SMTPS to a real mail server
                .when(simple(String.valueOf("dev".equals(activeProfile))))
                .to("smtp://{{smtp.config.host}}:{{smtp.config.port}}")
                .otherwise()
                .to("smtps://{{smtp.config.host}}:{{smtp.config.port}}"
                    + "?username={{smtp.config.username}}&password={{smtp.config.password}}")
                .end();

        // ### -------------- ###
    }
}
