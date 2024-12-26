/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
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

    @GET
    public List<HousingObject> getAllHousingObjects() {
        return housingObjectRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public HousingObject getHousingObject(@PathParam("id") long id) {
        return housingObjectRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createHousingObject(HousingObject housingObject) {
        housingObjectRepository.persist(housingObject);
        return Response.status(Response.Status.CREATED).entity(housingObject).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateHousingObject(@PathParam("id") long id, HousingObject housingObject) {
        HousingObject existingHousingObject = housingObjectRepository.findById(id);
        if (existingHousingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingHousingObject.setName(housingObject.getName());
        existingHousingObject.setStreet(housingObject.getStreet());
        existingHousingObject.setCity(housingObject.getCity());
        existingHousingObject.setState(housingObject.getState());
        existingHousingObject.setZipCode(housingObject.getZipCode());
        existingHousingObject.setNumberOfApartments(housingObject.getNumberOfApartments());
        housingObjectRepository.persist(existingHousingObject);
        return Response.ok(existingHousingObject).build();
    }

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
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */