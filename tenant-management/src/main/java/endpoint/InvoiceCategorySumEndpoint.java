/**
 * REST endpoint for retrieving the total sum of invoices by category for a specific housing object and year.
 * <p>
 * This endpoint provides a method to get the total sum of invoices for a given housing object,
 * invoice category, year, and relevance for the annual statement.
 *
 * @version 1.0
 * @see InvoiceCategorySumService
 * <p>
 * Authors:
 * - Zohal Mohammadi
 * - GitHub Copilot
 */
package endpoint;
/**
 * imports necessary classes, defines a REST endpoint class, and injects a service
 * InvoiceCategorySumService: A service class for handling business logic.
 * jakarta.inject.Inject: For dependency injection.
 * jakarta.ws.rs.*: For JAX-RS annotations.
 * jakarta.ws.rs.core.MediaType and jakarta.ws.rs.core.Response: For handling media types and HTTP responses.
 */

import service.InvoiceCategorySumService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Class Definition:
 *
 * @Path("/invoice-category-sum"): Defines the base URI for the endpoint.
 * @Produces(MediaType.APPLICATION_JSON): Specifies that the endpoint produces JSON responses.
 * @Consumes(MediaType.APPLICATION_JSON): Specifies that the endpoint consumes JSON requests.
 */
@Path("/invoice-category-sum")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceCategorySumEndpoint {

    /**
     * Dependency Injection:
     *
     * @Inject InvoiceCategorySumService invoiceCategorySumService;: Injects an instance of InvoiceCategorySumService into the endpoint class.
     */
    @Inject
    InvoiceCategorySumService invoiceCategorySumService;

    /**
     * Retrieves the total sum of invoices for a specific housing object, invoice category, year,
     * and relevance for the annual statement.
     *
     * @param housingObjectId            the ID of the housing object
     * @param categoryId                 the ID of the invoice category
     * @param year                       the year for which the total sum is calculated
     * @param relevantForAnnualStatement indicates if the invoice is relevant for the annual statement (mandatory)
     * @return the total sum of invoices rounded to two decimal places
     */
    @GET
    @Path("/{housingObjectId}/{invoiceCategoryId}/{year}")
    public Response getCategoryTotalSumByHousingObjectAndYear(@PathParam("housingObjectId") long housingObjectId, @PathParam("invoiceCategoryId") long categoryId, @PathParam("year") int year, @QueryParam("relevantForAnnualStatement") Boolean relevantForAnnualStatement) {
        if (relevantForAnnualStatement == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'relevantForAnnualStatement' is mandatory").build();
        }
        Double result = Math.round(invoiceCategorySumService.getCategoryTotalSumByHousingObjectAndYear(housingObjectId, categoryId, year, relevantForAnnualStatement) * 100) / 100.0;
        return Response.ok(result).build();
    }
}