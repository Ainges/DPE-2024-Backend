/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

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
        existingApartment.setHousingObject(apartment.getHousingObject());
        existingApartment.setAreaInM2(apartment.getAreaInM2());
        existingApartment.setNumberOfRooms(apartment.getNumberOfRooms());
        existingApartment.setColdRent(apartment.getColdRent());
        existingApartment.setHeatingCostPrepayment(apartment.getHeatingCostPrepayment());
        existingApartment.setAdditionalCostPrepayment(apartment.getAdditionalCostPrepayment());
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