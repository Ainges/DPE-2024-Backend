/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package endpoint;

import entity.Tenant;
import entity.RentalAgreement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.TenantRepository;
import repository.RentalAgreementRepository;

import java.util.List;
import java.util.Set;

@ApplicationScoped
@Path("/tenants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TenantEndpoint {

    @Inject
    TenantRepository tenantRepository;

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @GET
    public List<Tenant> getAllTenants() {
        return tenantRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Tenant getTenant(@PathParam("id") long id) {
        return tenantRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createTenant(Tenant tenant) {
        Set<RentalAgreement> agreements = tenant.getRentalAgreements();
        if (agreements != null && !agreements.isEmpty()) {
            for (RentalAgreement agreement : agreements) {
                RentalAgreement existingAgreement = rentalAgreementRepository.findById(agreement.getRentalAgreementId());
                if (existingAgreement != null) {
                    agreement = existingAgreement;
                }
            }
            tenant.setRentalAgreements(agreements); // Update the rental agreements set
        }
        tenantRepository.persist(tenant);
        return Response.status(Response.Status.CREATED).entity(tenant).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateTenant(@PathParam("id") long id, Tenant tenant) {
        Tenant existingTenant = tenantRepository.findById(id);
        if (existingTenant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingTenant.setFirstName(tenant.getFirstName());
        existingTenant.setLastName(tenant.getLastName());
        existingTenant.setEmail(tenant.getEmail());
        existingTenant.setPhoneNumber(tenant.getPhoneNumber());
        existingTenant.setActive(tenant.isActive());

        Set<RentalAgreement> agreements = tenant.getRentalAgreements();
        if (agreements != null && !agreements.isEmpty()) {
            for (RentalAgreement agreement : agreements) {
                RentalAgreement existingAgreement = rentalAgreementRepository.findById(agreement.getRentalAgreementId());
                if (existingAgreement != null) {
                    agreement = existingAgreement;
                }
            }
        }
        existingTenant.setRentalAgreements(agreements);

        tenantRepository.persist(existingTenant);
        return Response.ok(existingTenant).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTenant(@PathParam("id") long id) {
        Tenant tenant = tenantRepository.findById(id);
        if (tenant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        tenantRepository.delete(tenant);
        return Response.noContent().build();
    }
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */