/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.InvoiceCategory;
import entity.AnnualStatement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.InvoiceCategoryRepository;
import repository.AnnualStatementRepository;

import java.util.List;
import java.util.Set;

@ApplicationScoped
@Path("/invoice-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceCategoryEndpoint {

    @Inject
    InvoiceCategoryRepository invoiceCategoryRepository;

    @Inject
    AnnualStatementRepository annualStatementRepository;

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
        Set<AnnualStatement> statements = invoiceCategory.getAnnualStatements();
        if (statements != null && !statements.isEmpty()) {
            for (AnnualStatement statement : statements) {
                AnnualStatement existingStatement = annualStatementRepository.findById(statement.getAnnualStatementId());
                if (existingStatement != null) {
                    statement = existingStatement;
                }
            }
            invoiceCategory.setAnnualStatements(statements); // Update the annual statements set
        }
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
        existingInvoiceCategory.setName(invoiceCategory.getName());
        existingInvoiceCategory.setUnit(invoiceCategory.getUnit());
        existingInvoiceCategory.setDistributionKey(invoiceCategory.getDistributionKey());

        Set<AnnualStatement> statements = invoiceCategory.getAnnualStatements();
        if (statements != null && !statements.isEmpty()) {
            for (AnnualStatement statement : statements) {
                AnnualStatement existingStatement = annualStatementRepository.findById(statement.getAnnualStatementId());
                if (existingStatement != null) {
                    statement = existingStatement;
                }
            }
        }
        existingInvoiceCategory.setAnnualStatements(statements);

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