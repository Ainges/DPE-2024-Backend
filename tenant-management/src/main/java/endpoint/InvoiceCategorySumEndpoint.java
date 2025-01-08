package endpoint;

import service.InvoiceCategorySumService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST endpoint for retrieving the total sum of invoices for a given category.
 */
@Path("/invoice-category-sum")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceCategorySumEndpoint {

    @Inject
    InvoiceCategorySumService invoiceCategorySumService;

    /**
     * Endpoint to get the total sum of invoices for a given category by ID.
     *
     * @param categoryId the ID of the invoice category
     * @return a Response containing the total sum of invoice amounts for the given category
     */
    @GET
    @Path("/totalById")
    public Response getCategoryTotalSumById(@QueryParam("categoryId") long categoryId) {
        double totalSum = invoiceCategorySumService.getCategoryTotalSumById(categoryId);
        return Response.ok(totalSum).build();
    }
}
/**
 * End
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */