/**
 * Start
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */

package endpoint;

import dto.CreateStatementEntryServiceDTO;
import entity.RentalAgreement;
import service.CreateStatementEntryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST endpoint for managing statement entries.
 */
@ApplicationScoped
@Path("/create-statement-entries-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreateStatementEntryServiceEndpoint {

    @Inject
    CreateStatementEntryService createStatementEntryService;

    /**
     * Creates statement entries for rental agreements without tenant change.
     *
     * @param dto the data transfer object containing the necessary attributes
     * @return a Response indicating the result of the operation
     */
    @POST
    @Path("/no-tenant-change")
    @Transactional
    public Response createStatementEntryNoChange(CreateStatementEntryServiceDTO dto) {

        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            createStatementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement, dto.getRentalAgreements(), dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * MUST BE IMPLEMENTED
     * Creates statement entries for rental agreements with tenant change.
     *
     * @param dto the data transfer object containing the necessary attributes
     * @return a Response indicating the result of the operation
     */
    @POST
    @Path("/tenant-change")
    @Transactional
    public Response createStatementEntryChange(CreateStatementEntryServiceDTO dto) {

        List<RentalAgreement> rentalAgreementsWithoutChanges = dto.getRentalAgreements();
        List<RentalAgreement> rentalAgreementsWithChanges = null;

        //Get all rental agreements with the same apartment id
        for (RentalAgreement rentalAgreement : rentalAgreementsWithoutChanges) {
            long apartmentId = rentalAgreement.getApartment().getApartmentId();
            rentalAgreementsWithChanges = rentalAgreementsWithoutChanges.stream()
                    .filter(ra -> ra.getApartment().getApartmentId() == apartmentId)
                    .collect(Collectors.toList());
        }

        //Remove from rental agreements without changes
        rentalAgreementsWithoutChanges.removeAll(rentalAgreementsWithChanges);

        for (RentalAgreement rentalAgreement : rentalAgreementsWithoutChanges) {
            createStatementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement, dto.getRentalAgreements(), dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());
        }

        createStatementEntryService.divideInvoiceCategorySumMidYear(rentalAgreementsWithChanges, dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());


        return Response.status(Response.Status.ACCEPTED).build();
    }
}
/**
 * End
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */