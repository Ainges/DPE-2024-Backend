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
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.HousingObjectRepository;

import java.util.List;

@ApplicationScoped
@Path("/housing-objects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HousingObjectEndpoint {

    @Inject
    HousingObjectRepository housingObjectRepository;

    /**
     * Retrieves all housing objects.
     *
     * @return a list of all housing objects
     */
    @GET
    public List<HousingObject> getAllHousingObjects() {
        return housingObjectRepository.listAll();
    }

    /**
     * Retrieves a specific housing object by its ID.
     *
     * @param id the ID of the housing object
     * @return the housing object with the specified ID
     */
    @GET
    @Path("/{id}")
    public HousingObject getHousingObject(@PathParam("id") long id) {
        return housingObjectRepository.findById(id);
    }

    /**
     * Creates a new housing object.
     *
     * @param housingObject the housing object to create
     * @return a Response containing the created housing object
     */
    @POST
    @Transactional
    public Response createHousingObject(HousingObject housingObject) {
        housingObjectRepository.persist(housingObject);
        return Response.status(Response.Status.CREATED).entity(housingObject).build();
    }

    /**
     * Updates an existing housing object.
     *
     * @param id            the ID of the housing object to update
     * @param housingObject the updated housing object data
     * @return a Response containing the updated housing object,
     * or a 404 Not Found status if the housing object does not exist
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateHousingObject(@PathParam("id") long id, HousingObject housingObject) {
        HousingObject existingHousingObject = housingObjectRepository.findById(id);
        if (existingHousingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (housingObject.getName() != null) {
            existingHousingObject.setName(housingObject.getName());
        }
        if (housingObject.getStreet() != null) {
            existingHousingObject.setStreet(housingObject.getStreet());
        }
        if (housingObject.getCity() != null) {
            existingHousingObject.setCity(housingObject.getCity());
        }
        if (housingObject.getState() != null) {
            existingHousingObject.setState(housingObject.getState());
        }
        if (housingObject.getZipCode() != null) {
            existingHousingObject.setZipCode(housingObject.getZipCode());
        }
        if (housingObject.getNumberOfApartments() != 0) {
            existingHousingObject.setNumberOfApartments(housingObject.getNumberOfApartments());
        }
        housingObjectRepository.persist(existingHousingObject);
        return Response.ok(existingHousingObject).build();
    }

    /**
     * Deletes an existing housing object.
     *
     * @param id the ID of the housing object to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the housing object does not exist
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteHousingObject(@PathParam("id") long id) {
        HousingObject housingObject = housingObjectRepository.findById(id);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        housingObjectRepository.delete(housingObject);
        return Response.noContent().build();
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */