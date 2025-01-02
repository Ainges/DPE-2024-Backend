/**
 * Start
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */

package endpoint;

import dto.StatementEntryServiceDTO;
import entity.RentalAgreement;
import service.StatementEntryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST endpoint for managing statement entries.
 */
@ApplicationScoped
@Path("/statement-entries-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatementEntryServiceEndpoint {

    @Inject
    StatementEntryService statementEntryService;

    /**
     * Creates statement entries for rental agreements without tenant change.
     *
     * @param dto the data transfer object containing the necessary attributes
     * @return a Response indicating the result of the operation
     */
    @POST
    @Path("/no-tenant-change")
    @Transactional
    public Response createStatementEntryNoChange(StatementEntryServiceDTO dto) {
        statementEntryService.setDistributionKey(dto.getDistributionKey());
        statementEntryService.setInvoiceCategoryName(dto.getInvoiceCategoryName());
        statementEntryService.setInvoiceCategorySum(dto.getInvoiceCategorySum());
        statementEntryService.setHousingObject(dto.getHousingObject());
        statementEntryService.setRentalAgreements(dto.getRentalAgreements());

        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            statementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement);
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
    public Response createStatementEntryChange(StatementEntryServiceDTO dto) {
        statementEntryService.setDistributionKey(dto.getDistributionKey());
        statementEntryService.setInvoiceCategoryName(dto.getInvoiceCategoryName());
        statementEntryService.setInvoiceCategorySum(dto.getInvoiceCategorySum());
        statementEntryService.setHousingObject(dto.getHousingObject());
        statementEntryService.setRentalAgreements(dto.getRentalAgreements());

        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            statementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement);
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
/**
 * End
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */