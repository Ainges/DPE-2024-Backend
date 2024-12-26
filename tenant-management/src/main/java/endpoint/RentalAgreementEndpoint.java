/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package endpoint;

import entity.RentalAgreement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.RentalAgreementRepository;

import java.util.List;

@ApplicationScoped
@Path("/rental-agreements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentalAgreementEndpoint {

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @GET
    public List<RentalAgreement> getAllRentalAgreements() {
        return rentalAgreementRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public RentalAgreement getRentalAgreement(@PathParam("id") long id) {
        return rentalAgreementRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createRentalAgreement(RentalAgreement rentalAgreement) {
        rentalAgreementRepository.persist(rentalAgreement);
        return Response.status(Response.Status.CREATED).entity(rentalAgreement).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateRentalAgreement(@PathParam("id") long id, RentalAgreement rentalAgreement) {
        RentalAgreement existingRentalAgreement = rentalAgreementRepository.findById(id);
        if (existingRentalAgreement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingRentalAgreement.setTenant(rentalAgreement.getTenant());
        existingRentalAgreement.setApartment(rentalAgreement.getApartment());
        existingRentalAgreement.setStartDate(rentalAgreement.getStartDate());
        existingRentalAgreement.setEndDate(rentalAgreement.getEndDate());
        rentalAgreementRepository.persist(existingRentalAgreement);
        return Response.ok(existingRentalAgreement).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteRentalAgreement(@PathParam("id") long id) {
        RentalAgreement rentalAgreement = rentalAgreementRepository.findById(id);
        if (rentalAgreement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        rentalAgreementRepository.delete(rentalAgreement);
        return Response.noContent().build();
    }
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */