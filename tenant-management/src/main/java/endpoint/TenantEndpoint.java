/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
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

/**
 * defines a JAX-RS REST endpoint
 * @ApplicationScoped: Indicates that the bean is application-scoped, meaning it will be created once and shared across the entire application.
 * @Path("/tenants"): Specifies the base URI path for the REST endpoint. In this case, it maps to /tenants.
 * @Produces(MediaType.APPLICATION_JSON): Specifies that the endpoint will produce responses in JSON format.
 * @Consumes(MediaType.APPLICATION_JSON): Specifies that the endpoint will consume requests in JSON format.
 * /public class TenantEndpoint: Declares the class as a public class named TenantEndpoint, which contains the RESTful methods for handling tenant-related operations.
 */
@ApplicationScoped
@Path("/tenants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TenantEndpoint {

    @Inject
    TenantRepository tenantRepository;

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    /**
     * Retrieves all tenants.
     *
     * @return a list of all tenants
     */
    @GET
    public List<Tenant> getAllTenants() {
        return tenantRepository.listAll();
    }

    /**
     * Retrieves a specific tenant by its ID.
     *
     * @param id the ID of the tenant
     * @return the tenant with the specified ID
     */
    @GET
    @Path("/{id}")
    public Tenant getTenant(@PathParam("id") long id) {
        return tenantRepository.findById(id);
    }

    /**
     * Creates a new tenant.
     *
     * @param tenant the tenant to create
     * @return a Response containing the created tenant
     */
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
        if (tenant.getFirstName() != null) {
            existingTenant.setFirstName(tenant.getFirstName());
        }
        if (tenant.getLastName() != null) {
            existingTenant.setLastName(tenant.getLastName());
        }
        if (tenant.getEmail() != null) {
            existingTenant.setEmail(tenant.getEmail());
        }
        if (tenant.getPhoneNumber() != null) {
            existingTenant.setPhoneNumber(tenant.getPhoneNumber());
        }
        existingTenant.setActive(tenant.isActive());

        Set<RentalAgreement> agreements = tenant.getRentalAgreements();
        if (agreements != null && !agreements.isEmpty()) {
            for (RentalAgreement agreement : agreements) {
                RentalAgreement existingAgreement = rentalAgreementRepository.findById(agreement.getRentalAgreementId());
                if (existingAgreement != null) {
                    agreement = existingAgreement;
                }
            }
            existingTenant.setRentalAgreements(agreements);
        }

        tenantRepository.persist(existingTenant);
        return Response.ok(existingTenant).build();
    }

    /**
     * Deletes an existing tenant.
     *
     * @param id the ID of the tenant to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the tenant does not exist
     */
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
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */