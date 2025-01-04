package endpoint;

import service.InvoiceCategorySumService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/invoice-category-sum")
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceCategorySumEndpoint {

    @Inject
    InvoiceCategorySumService invoiceCategorySumService;

    /**
     * Endpoint to get the total sum of invoices for a given category.
     *
     * @param categoryName the name of the invoice category
     * @return a Response containing the total sum of invoice amounts for the given category
     */
    @GET
    @Path("/total")
    public Response getCategoryTotalSum(@QueryParam("categoryName") String categoryName) {
        double totalSum = invoiceCategorySumService.getCategoryTotalSum(categoryName);
        return Response.ok(totalSum).build();
    }
}