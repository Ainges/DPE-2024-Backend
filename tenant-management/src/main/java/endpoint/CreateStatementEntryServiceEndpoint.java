/**
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */

package endpoint;

import dto.CreateStatementEntryServiceDTO;
import entity.RentalAgreement;
import entity.StatementEntry;
import service.CreateStatementEntryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.ArrayList;
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
        List<StatementEntry> statementEntries = new ArrayList<>();
        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            List<StatementEntry> createdStatementEntries = createStatementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement, dto.getRentalAgreements(), dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());
            for (StatementEntry statementEntry : createdStatementEntries) {
                statementEntries.add(statementEntry);
            }
        }
        return Response.status(Response.Status.CREATED).entity(statementEntries).build();
    }

    /**
     * Creates statement entries for rental agreements with tenant change.
     *
     * @param dto the data transfer object containing the necessary attributes
     * @return a Response indicating the result of the operation
     */
    @POST
    @Path("/tenant-change")
    @Transactional
    public Response createStatementEntryChange(CreateStatementEntryServiceDTO dto) throws ParseException {

        List<RentalAgreement> allRentalAgreements = new ArrayList<>(dto.getRentalAgreements());
        List<RentalAgreement> rentalAgreementsWithoutChanges = dto.getRentalAgreements();
        List<RentalAgreement> rentalAgreementsWithChanges = new ArrayList<>();
        List<StatementEntry> statementEntries = new ArrayList<>();

        //Get all rental agreements with the same apartment id
        for (RentalAgreement rentalAgreement : rentalAgreementsWithoutChanges) {
            long apartmentId = rentalAgreement.getApartment().getApartmentId();
            rentalAgreementsWithChanges = rentalAgreementsWithoutChanges.stream()
                    .filter(ra -> ra.getApartment().getApartmentId() == apartmentId)
                    .collect(Collectors.toList());
        }

        //Remove from rental agreements without changes
        rentalAgreementsWithoutChanges.removeAll(rentalAgreementsWithChanges);

        //Handle Rental Agreements without tenant changes
        for (RentalAgreement rentalAgreement : rentalAgreementsWithoutChanges) {
            List<StatementEntry> createdStatementEntries = createStatementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement, allRentalAgreements, dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());
            for (StatementEntry statementEntry : createdStatementEntries) {
                statementEntries.add(statementEntry);
            }
        }
        //Handle Rental Agreements with tenant changes
        List<StatementEntry> createdStatementEntries = createStatementEntryService.divideInvoiceCategorySumMidYear(rentalAgreementsWithChanges, allRentalAgreements, dto.getHousingObject(), dto.getDistributionKey(), dto.getInvoiceCategorySum(), dto.getInvoiceCategoryName(), dto.getAnnualStatementPeriod());
        for (StatementEntry statementEntry : createdStatementEntries) {
            statementEntries.add(statementEntry);
        }
        //Response.status(Response.Status.CREATED): Sets the HTTP status code to 201 Created.
        //.entity(statementEntries): Sets the response body to the statementEntries list, which will be serialized to JSON.
        //.build(): Builds the Response object with the specified status and entity.
        return Response.status(Response.Status.CREATED).entity(statementEntries).build();
    }
}
