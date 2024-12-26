/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
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

    @GET
    public List<Apartment> getAllApartments() {
        return apartmentRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Apartment getApartment(@PathParam("id") long id) {
        return apartmentRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createApartment(Apartment apartment) {
        apartmentRepository.persist(apartment);
        return Response.status(Response.Status.CREATED).entity(apartment).build();
    }

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
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */