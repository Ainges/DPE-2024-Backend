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

@ApplicationScoped
@Path("/statement-entries-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatementEntryServiceEndpoint {

    @Inject
    StatementEntryService statementEntryService;

    @POST
    @Path("/no-tenant-change")
    @Transactional
    public Response createStatementEntryNoChange(StatementEntryServiceDTO dto) {
        statementEntryService = new StatementEntryService(
                dto.getDistributionKey(),
                dto.getInvoiceCategoryName(),
                dto.getInvoiceCategorySum(),
                dto.getHousingObject(),
                dto.getRentalAgreements()
        );
        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            statementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement);
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/tenant-change")
    @Transactional
    public Response createStatementEntryChange(StatementEntryServiceDTO dto) {
        statementEntryService = new StatementEntryService(
                dto.getDistributionKey(),
                dto.getInvoiceCategoryName(),
                dto.getInvoiceCategorySum(),
                dto.getHousingObject(),
                dto.getRentalAgreements()
        );
        for (RentalAgreement rentalAgreement : dto.getRentalAgreements()) {
            statementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement);
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }
}