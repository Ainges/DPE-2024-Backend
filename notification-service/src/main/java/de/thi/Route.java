package de.thi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Processor;
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
                .to("direct:notificationSort");


        // ### -------------- ###
        // ### Internal Routes ###

        // de.thi.Route for sorting the notification
        from("direct:notificationSort")
                .choice()
                //
                    .when(jsonpath("$.mailType").isEqualTo("type1")).to("direct:route1")
                    .when(jsonpath("$.mailType").isEqualTo("type2")).to("direct:route2")
                    .when(jsonpath("$.mailType").isEqualTo("type3")).to("direct:route3")
                    .when(jsonpath("$.mailType").isEqualTo("type4")).to("direct:route4")
                    .when(jsonpath("$.mailType").isEqualTo("type5")).to("direct:route5")

                    //Send to DLQ (Dead-Letter-Queue) if the notification type is not supported
                    .otherwise().to("activemq:queue:deadLetterQueue");


        from("direct:route1")
                .routeId("route1-de.thi.Route")
                // write json body to xml in fs
                .to("direct:print-raw-notification-to-xml")
                //TODO: implement Processor for Building the Mail
                .process(annualStatementPreparationReminderProcessor)
                .log("route1")
                .to("direct:sendMail");

        from("direct:route2")
                .routeId("route2-de.thi.Route")
                .to("direct:print-raw-notification-to-xml")
                .setBody(simple("route2 received"))
                .log("route2")
                .to("direct:sendMail");

        from("direct:route3")
                .routeId("route3-de.thi.Route")
                .setBody(simple("route3 received"))
                .log("route3")
                .to("direct:sendMail");

        from("direct:route4")
                .routeId("route4-de.thi.Route")
                .setBody(simple("route4 received"))
                .log("route4")
                .to("direct:sendMail");

        from("direct:route5")
                .routeId("route5-de.thi.Route")
                .setBody(simple("route5 received"))
                .log("route5")
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

        // de.thi.Route for sending the mail
        from("direct:sendMail")
                //TODO: Remove this part after testing
                .process(exchange -> {
                    exchange.getIn().setHeader("Subject", "DPE-2024 Notification");
                    exchange.getIn().setHeader("To", "info@test.de");
                    exchange.getIn().setHeader("From", "info.dpe2024@gmail.com");
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
