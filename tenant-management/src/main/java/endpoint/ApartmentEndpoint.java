/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;
//Imports:
    // entity.Apartment: Represents the Apartment entity.
    //jakarta.enterprise.context.ApplicationScoped: Specifies that the bean is application-scoped.
    //jakarta.inject.Inject: Used for dependency injection.
    //jakarta.transaction.Transactional: Marks methods as transactional.
    //jakarta.ws.rs.*: Provides JAX-RS annotations for RESTful web services.
    //jakarta.ws.rs.core.MediaType and jakarta.ws.rs.core.Response: Used for specifying media types and creating HTTP responses.
    //repository.ApartmentRepository: Represents the repository for Apartment entities.
    //java.util.List: Used for handling lists of apartments.
//Class Definition:
    //@ApplicationScoped: Indicates that the ApartmentEndpoint bean is application-scoped.
    //@Path("/apartments"): Specifies the base URI path for the endpoint.
    //@Produces(MediaType.APPLICATION_JSON) and @Consumes(MediaType.APPLICATION_JSON): Specifies that the endpoint produces and consumes JSON.
//Dependency Injection:
    //@Inject ApartmentReposit
import entity.Apartment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.ApartmentRepository;

import java.util.List;

@ApplicationScoped
@Path("/apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentEndpoint {

    @Inject
    ApartmentRepository apartmentRepository;

    /**
     * Retrieves all apartments.
     *
     * @return a list of all apartments
     */
    @GET
    public List<Apartment> getAllApartments() {
        return apartmentRepository.listAll();
    }

    /**
     * Retrieves a specific apartment by its ID.
     *
     * @param id the ID of the apartment
     * @return the apartment with the specified ID
     */
    @GET
    @Path("/{id}")
    public Apartment getApartment(@PathParam("id") long id) {
        return apartmentRepository.findById(id);
    }

    /**
     * Creates a new apartment.
     *
     * @param apartment the apartment to create
     * @return a Response containing the created apartment
     */
    @POST
    @Transactional
    public Response createApartment(Apartment apartment) {
        apartmentRepository.persist(apartment);
        return Response.status(Response.Status.CREATED).entity(apartment).build();
    }

    /**
     * Updates an existing apartment.
     *
     * @param id        the ID of the apartment to update
     * @param apartment the updated apartment data
     * @return a Response containing the updated apartment,
     * or a 404 Not Found status if the apartment does not exist
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateApartment(@PathParam("id") long id, Apartment apartment) {
        Apartment existingApartment = apartmentRepository.findById(id);
        if (existingApartment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (apartment.getHousingObject() != null) {
            existingApartment.setHousingObject(apartment.getHousingObject());
        }
        if (apartment.getAreaInM2() != 0.0) {
            existingApartment.setAreaInM2(apartment.getAreaInM2());
        }
        if (apartment.getNumberOfRooms() != 0) {
            existingApartment.setNumberOfRooms(apartment.getNumberOfRooms());
        }
        if (apartment.getColdRent() != 0.0) {
            existingApartment.setColdRent(apartment.getColdRent());
        }
        if (apartment.getHeatingCostPrepayment() != 0.0) {
            existingApartment.setHeatingCostPrepayment(apartment.getHeatingCostPrepayment());
        }
        if (apartment.getAdditionalCostPrepayment() != 0.0) {
            existingApartment.setAdditionalCostPrepayment(apartment.getAdditionalCostPrepayment());
        }
        apartmentRepository.persist(existingApartment);
        return Response.ok(existingApartment).build();
    }

    /**
     * Deletes an existing apartment.
     *
     * @param id the ID of the apartment to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the apartment does not exist
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteApartment(@PathParam("id") long id) {
        Apartment apartment = apartmentRepository.findById(id);
        if (apartment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        apartmentRepository.delete(apartment);
        return Response.noContent().build();
    }
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */