/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
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

    /**
     * Retrieves all rental agreements.
     *
     * @return a list of all rental agreements
     */
    @GET
    public List<RentalAgreement> getAllRentalAgreements() {
        return rentalAgreementRepository.listAll();
    }

    /**
     * Retrieves a specific rental agreement by its ID.
     *
     * @param id the ID of the rental agreement
     * @return the rental agreement with the specified ID
     */
    @GET
    @Path("/{id}")
    public RentalAgreement getRentalAgreement(@PathParam("id") long id) {
        return rentalAgreementRepository.findById(id);
    }

    /**
     * Retrieves rental agreements based on the apartment ID.
     *
     * @param apartmentId the ID of the apartment to filter rental agreements
     * @return a Response containing a list of rental agreements for the specified apartment,
     * or a 404 Not Found status if no rental agreements are found
     */
    @GET
    @Path("/by-apartment/{apartmentId}")
    public Response getRentalAgreementsByApartmentId(@PathParam("apartmentId") long apartmentId) {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.find("apartment.id", apartmentId).list();
        if (rentalAgreements.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(rentalAgreements).build();
    }

    /**
     * Retrieves rental agreements based on the housing object ID.
     *
     * @param housingObjectId the ID of the housing object to filter rental agreements
     * @return a Response containing a list of rental agreements for the specified housing object,
     * or a 404 Not Found status if no rental agreements are found
     */
    @GET
    @Path("/by-housing-object/{housingObjectId}")
    public Response getRentalAgreementsByHousingObjectId(@PathParam("housingObjectId") long housingObjectId) {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.find(
                "apartment.housingObject.housingObjectId", housingObjectId).list();
        if (rentalAgreements.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(rentalAgreements).build();
    }

    /**
     * Creates a new rental agreement.
     *
     * @param rentalAgreement the rental agreement to create
     * @return a Response containing the created rental agreement
     */
    @POST
    @Transactional
    public Response createRentalAgreement(RentalAgreement rentalAgreement) {
        Set<Tenant> tenants = rentalAgreement.getTenants();
        if (tenants != null && !tenants.isEmpty()) {
            for (Tenant tenant : tenants) {
                Tenant existingTenant = tenantRepository.findById(tenant.getTenantId());
                if (existingTenant != null) {
                    tenant = existingTenant;
                }
            }
            rentalAgreement.setTenants(tenants); // Update the tenants set
        }

        rentalAgreementRepository.persist(rentalAgreement);
        return Response.status(Response.Status.CREATED).entity(rentalAgreement).build();
    }

    /**
     * Updates an existing rental agreement.
     *
     * @param id              the ID of the rental agreement to update
     * @param rentalAgreement the updated rental agreement data
     * @return a Response containing the updated rental agreement,
     * or a 404 Not Found status if the rental agreement does not exist
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateRentalAgreement(@PathParam("id") long id, RentalAgreement rentalAgreement) {
        RentalAgreement existingRentalAgreement = rentalAgreementRepository.findById(id);
        if (existingRentalAgreement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (rentalAgreement.getApartment() != null) {
            existingRentalAgreement.setApartment(rentalAgreement.getApartment());
        }
        if (rentalAgreement.getStartDate() != null) {
            existingRentalAgreement.setStartDate(rentalAgreement.getStartDate());
        }
        if (rentalAgreement.getEndDate() != null) {
            existingRentalAgreement.setEndDate(rentalAgreement.getEndDate());
        }
        Set<Tenant> tenants = rentalAgreement.getTenants();
        if (tenants != null && !tenants.isEmpty()) {
            for (Tenant tenant : tenants) {
                Tenant existingTenant = tenantRepository.findById(tenant.getTenantId());
                if (existingTenant != null) {
                    tenant = existingTenant;
                }
            }
            existingRentalAgreement.setTenants(tenants);
        }
        rentalAgreementRepository.persist(existingRentalAgreement);
        return Response.ok(existingRentalAgreement).build();
    }

    /**
     * Deletes an existing rental agreement.
     *
     * @param id the ID of the rental agreement to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the rental agreement does not exist
     */
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
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */