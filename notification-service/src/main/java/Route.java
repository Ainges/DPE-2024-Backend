import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/api/")
            .post("/notification")
            .to("direct:notification");

        from("direct:notification")
            .log("Notification received")
                .setBody(constant("Notification received"))
            .to("mock:notification");
    }
}
