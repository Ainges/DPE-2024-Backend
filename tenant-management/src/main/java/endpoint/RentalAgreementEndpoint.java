/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package endpoint;

import entity.RentalAgreement;
import entity.Tenant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.RentalAgreementRepository;
import repository.TenantRepository;

import java.util.List;
import java.util.Set;

@ApplicationScoped
@Path("/rental-agreements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentalAgreementEndpoint {

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @Inject
    TenantRepository tenantRepository;

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
        Set<Tenant> tenants = rentalAgreement.getTenants();
        for (Tenant tenant : tenants) {
            Tenant existingTenant = tenantRepository.findById(tenant.getTenantId());
            if (existingTenant != null) {
                tenant = existingTenant;
            }
        }
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
        existingRentalAgreement.setApartment(rentalAgreement.getApartment());
        existingRentalAgreement.setStartDate(rentalAgreement.getStartDate());
        existingRentalAgreement.setEndDate(rentalAgreement.getEndDate());

        Set<Tenant> tenants = rentalAgreement.getTenants();
        for (Tenant tenant : tenants) {
            Tenant existingTenant = tenantRepository.findById(tenant.getTenantId());
            if (existingTenant != null) {
                tenant = existingTenant;
            }
        }
        existingRentalAgreement.setTenants(tenants);

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