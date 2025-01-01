package endpoint;

import dto.TestEndpoint.HeartbeatMessageDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/test")
public class TestEndpoint {

    @GET
    @Path("/heartbeat")
    public HeartbeatMessageDto getHHeartbeat() {
        return new HeartbeatMessageDto("I'm alive!");

    }
}
