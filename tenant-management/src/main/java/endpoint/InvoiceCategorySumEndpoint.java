package endpoint;

import jakarta.ws.rs.*;
import service.InvoiceCategorySumService;
import jakarta.inject.Inject;
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
     * Endpoint to get the total sum of invoices for a given category by name.
     *
     * @param categoryName the name of the invoice category
     * @return a Response containing the total sum of invoice amounts for the given category
     */
    @GET
    @Path("/totalByName")
    public Response getCategoryTotalSumByName(@QueryParam("categoryName") String categoryName) {
        double totalSum = invoiceCategorySumService.getCategoryTotalSumByName(categoryName);
        return Response.ok(totalSum).build();
    }
}
/**
 * End
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */