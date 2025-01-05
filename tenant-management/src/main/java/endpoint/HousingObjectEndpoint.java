/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.HousingObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.HousingObjectService;

import java.util.List;

@ApplicationScoped
@Path("/housing-objects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HousingObjectEndpoint {

    @Inject
    HousingObjectService housingObjectService;

    @GET
    public List<HousingObject> getAllHousingObjects() {
        return housingObjectService.getAllHousingObjects();
    }

    @GET
    @Path("/{id}")
    public Response getHousingObject(@PathParam("id") long id) {
        HousingObject housingObject = housingObjectService.getHousingObject(id);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(housingObject).build();
    }

    @POST
    public Response createHousingObject(HousingObject housingObject) {
        HousingObject createdHousingObject = housingObjectService.createHousingObject(housingObject);
        return Response.status(Response.Status.CREATED).entity(createdHousingObject).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateHousingObject(@PathParam("id") long id, HousingObject housingObject) {
        HousingObject updatedHousingObject = housingObjectService.updateHousingObject(id, housingObject);
        if (updatedHousingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedHousingObject).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHousingObject(@PathParam("id") long id) {
        boolean deleted = housingObjectService.deleteHousingObject(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */