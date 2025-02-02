/**
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
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

/**
 * Define a JAX-RS RESTful web service endpoint
 *
 * @ApplicationScoped: Indicates that the class is a CDI (Contexts and Dependency Injection) bean with application scope.
 * @Path("/housing-objects"): Specifies the base URI path for the RESTful web service.
 * @Produces(MediaType.APPLICATION_JSON): Specifies that the methods in this class produce JSON responses.
 * @Consumes(MediaType.APPLICATION_JSON): Specifies that the methods in this class consume JSON requests
 */
@ApplicationScoped
@Path("/housing-objects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

/**
 * The HousingObjectEndpoint class defines a JAX-RS RESTful web service endpoint in a Spring Boot application.
 * It provides CRUD operations for HousingObject entities.
 * getAllHousingObjects(): Handles HTTP GET requests to retrieve all HousingObject entities.
 * getHousingObject(long id): Handles HTTP GET requests to retrieve a specific HousingObject by its ID.
 * createHousingObject(HousingObject housingObject): Handles HTTP POST requests to create a new HousingObject.
 * updateHousingObject(long id, HousingObject housingObject): Handles HTTP PUT requests to update an existing HousingObject by its ID.
 * deleteHousingObject(long id): Handles HTTP DELETE requests to delete a specific HousingObject by its ID.
 */
public class HousingObjectEndpoint {

    @Inject
    HousingObjectService housingObjectService;

    /**
     * Retrieves all housing objects.
     *
     * @return a list of all housing objects
     */
    @GET
    public List<HousingObject> getAllHousingObjects() {
        return housingObjectService.getAllHousingObjects();
    }

    /**
     * Retrieves a housing object by its ID.
     *
     * @param id the ID of the housing object
     * @return a response containing the housing object with the specified ID
     */
    @GET
    @Path("/{id}")
    public Response getHousingObject(@PathParam("id") long id) {
        HousingObject housingObject = housingObjectService.getHousingObject(id);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(housingObject).build();
    }

    /**
     * Creates a new housing object.
     *
     * @param housingObject the housing object to create
     * @return a response containing the created housing object
     */
    @POST
    public Response createHousingObject(HousingObject housingObject) {
        HousingObject createdHousingObject = housingObjectService.createHousingObject(housingObject);
        return Response.status(Response.Status.CREATED).entity(createdHousingObject).build();
    }

    /**
     * Updates an existing housing object.
     *
     * @param id            the ID of the housing object to update
     * @param housingObject the updated housing object data
     * @return a response containing the updated housing object
     */
    @PUT
    @Path("/{id}")
    public Response updateHousingObject(@PathParam("id") long id, HousingObject housingObject) {
        HousingObject updatedHousingObject = housingObjectService.updateHousingObject(id, housingObject);
        if (updatedHousingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedHousingObject).build();
    }

    /**
     * Deletes a housing object by its ID.
     *
     * @param id the ID of the housing object to delete
     * @return a response indicating the result of the deletion
     */
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