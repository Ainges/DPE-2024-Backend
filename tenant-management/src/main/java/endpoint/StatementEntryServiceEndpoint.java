package endpoint;

import entity.RentalAgreement;
import entity.StatementEntry;
import service.StatementEntryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/statement-entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatementEntryServiceEndpoint {

    @POST
    @Path("/no-tenant-change")
    @Transactional
    public Response createStatementEntry(StatementEntryService statementEntryService) {
        for(RentalAgreement rentalAgreement : statementEntryService.getRentalAgreements()) {
            statementEntryService.divideInvoiceCategorySumWholeYear(rentalAgreement);
        }

        return Response.status(Response.Status.ACCEPTED).build();
    }
}