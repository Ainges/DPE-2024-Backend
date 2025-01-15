/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.InvoiceCategory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.InvoiceCategoryRepository;

import java.util.List;

/**
 * selected code defines a JAX-RS REST endpoint for managing invoice categories.
 * @ApplicationScoped: This annotation indicates that the InvoiceCategoryEndpoint class is a CDI (Contexts and Dependency Injection) bean with application scope, meaning it will be instantiated once and shared across the application.
 * @Path("/invoice-categories"): This annotation specifies the base URI path for the REST endpoint. All methods in this class will be relative to /invoice-categories.
 * @Produces(MediaType.APPLICATION_JSON): This annotation indicates that the methods in this class will produce responses in JSON format.
 * @Consumes(MediaType.APPLICATION_JSON): This annotation indicates that the methods in this class will consume requests in JSON format.
 * public class InvoiceCategoryEndpoint: This is the class declaration for the REST endpoint..
 */
@ApplicationScoped
@Path("/invoice-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceCategoryEndpoint {

    /**
     *  @Inject InvoiceCategoryRepository invoiceCategoryRepository;: This line injects an instance of InvoiceCategoryRepository into the endpoint, allowing it to interact with the data repository for invoice categories.
     */
    @Inject
    InvoiceCategoryRepository invoiceCategoryRepository;

    /**
     * Retrieves all invoice categories.
     *
     * @return a list of all invoice categories
     */
    @GET
    public List<InvoiceCategory> getAllInvoiceCategories() {
        return invoiceCategoryRepository.listAll();
    }

    /**
     * Retrieves a specific invoice category by its ID.
     *
     * @param id the ID of the invoice category
     * @return the invoice category with the specified ID
     */
    @GET
    @Path("/{id}")
    public InvoiceCategory getInvoiceCategory(@PathParam("id") long id) {
        return invoiceCategoryRepository.findById(id);
    }

    /**
     * Creates a new invoice category.
     *
     * @param invoiceCategory the invoice category to create
     * @return a Response containing the created invoice category
     */
    @POST
    @Transactional
    public Response createInvoiceCategory(InvoiceCategory invoiceCategory) {
        invoiceCategoryRepository.persist(invoiceCategory);
        return Response.status(Response.Status.CREATED).entity(invoiceCategory).build();
    }

    /**
     * Updates an existing invoice category.
     *
     * @param id the ID of the invoice category to update
     * @param invoiceCategory the updated invoice category data
     * @return a Response containing the updated invoice category,
     *         or a 404 Not Found status if the invoice category does not exist
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateInvoiceCategory(@PathParam("id") long id, InvoiceCategory invoiceCategory) {
        InvoiceCategory existingInvoiceCategory = invoiceCategoryRepository.findById(id);
        if (existingInvoiceCategory == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (invoiceCategory.getName() != null) {
            existingInvoiceCategory.setName(invoiceCategory.getName());
        }
        invoiceCategoryRepository.persist(existingInvoiceCategory);
        return Response.ok(existingInvoiceCategory).build();
    }

    /**
     * Deletes an existing invoice category.
     *
     * @param id the ID of the invoice category to delete
     * @return a Response with no content if the deletion was successful,
     *         or a 404 Not Found status if the invoice category does not exist
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteInvoiceCategory(@PathParam("id") long id) {
        InvoiceCategory invoiceCategory = invoiceCategoryRepository.findById(id);
        if (invoiceCategory == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        invoiceCategoryRepository.delete(invoiceCategory);
        return Response.noContent().build();
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */