import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

    @Override
    public void configure() throws Exception {

        rest("/api/")
                .post("notification")
                .type(NotificationDto.class)
                .consumes("application/json")
                .produces("application/json")
                .to("direct:notificationSort");


        from("activemq:queue:notification")
                .routeId("notification-from-Queue-Route")
                .log("Notification received")
                .to("direct:notificationSort");

        // Route for sorting the notification
        from("direct:notificationSort")
                .routeId("notificationSort-Route")
                .log("Notification to Sort received")

                // Unmarshal the incoming JSON to a NotificationDto object
                .unmarshal().json(NotificationDto.class)

                .process(exchange -> {

                    // Set the type of the notification as header, so that the content based routing can be done
                    NotificationDto notificationDto = exchange.getMessage().getBody(NotificationDto.class);
                    exchange.getMessage().setHeader("type", notificationDto.getType());
                })
                .choice()

                // ## Content based routing

                // Request Confirmation Route
                // Erste Variante mit when
                .when(header("type").isEqualTo("request-confirmation"))
                .setBody(simple("Request Confirmation Notification received"))
                .to("direct:requestConfirmation")

                // Reservation Confirmation Route
                // Zweite Variante mit when
                .when(header("type").isEqualTo("reservation-confirmation"))
                .setBody(simple("Reservation Confirmation Notification received"))
                .to("direct:reservationConfirmation")

                // Default Route
                // Wenn der Typ nicht erkannt wird, wird eine Log-Nachricht ausgegeben
                .otherwise()
                .log("Notification type not recognized")
                .end();


        from("direct:requestConfirmation")
                .routeId("sendRequestConfirmation-Route")
                .setBody(simple("Request Confirmation Notification received"))
                .log("Request Confirmation Notification received")
                .to("direct:sendMail");

        from("direct:reservationConfirmation")
                .routeId("sendReservationConfirmation-Route")
                .setBody(simple("Reservation Confirmation Notification received"))
                .log("Reservation Confirmation Notification received")
                .to("direct:sendMail");


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
    }
}
