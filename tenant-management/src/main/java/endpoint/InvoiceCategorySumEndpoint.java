package endpoint;

import service.InvoiceCategorySumService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceCategorySumEndpoint {

    @Inject
    InvoiceCategorySumService invoiceCategorySumService;

    @GET
    @Path("/sum/{housingObjectId}/{invoiceCategoryId}/{year}")
    public Response getCategoryTotalSumByHousingObjectAndYear(@PathParam("housingObjectId") long housingObjectId, @PathParam("invoiceCategoryId") long categoryId, @PathParam("year") int year) {
        Map<Long, Double> result = invoiceCategorySumService.getCategoryTotalSumByHousingObjectAndYear(housingObjectId, categoryId, year);
        return Response.ok(result).build();
    }
}
/**
 * End
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */