/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.AnnualStatement;
import entity.InvoiceCategory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.AnnualStatementRepository;
import repository.InvoiceCategoryRepository;

import java.util.List;
import java.util.Set;

@ApplicationScoped
@Path("/annual-statements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnualStatementEndpoint {

    @Inject
    AnnualStatementRepository annualStatementRepository;

    @Inject
    InvoiceCategoryRepository invoiceCategoryRepository;

    /**
     * Retrieves all annual statements.
     *
     * @return a list of all annual statements
     */
    @GET
    public List<AnnualStatement> getAllAnnualStatements() {
        return annualStatementRepository.listAll();
    }

    /**
     * Retrieves a specific annual statement by its ID.
     *
     * @param id the ID of the annual statement
     * @return the annual statement with the specified ID
     */
    @GET
    @Path("/{id}")
    public AnnualStatement getAnnualStatement(@PathParam("id") long id) {
        return annualStatementRepository.findById(id);
    }

    /**
     * Creates a new annual statement.
     *
     * @param annualStatement the annual statement to create
     * @return a Response containing the created annual statement
     */
    @POST
    @Transactional
    public Response createAnnualStatement(AnnualStatement annualStatement) {
        Set<InvoiceCategory> categories = annualStatement.getInvoiceCategories();
        if (categories != null && !categories.isEmpty()) {
            for (InvoiceCategory category : categories) {
                InvoiceCategory existingCategory = invoiceCategoryRepository.findById(category.getInvoiceCategoryId());
                if (existingCategory != null) {
                    category = existingCategory;
                }
            }
            annualStatement.setInvoiceCategories(categories); // Update the invoice categories set
        }
        annualStatementRepository.persist(annualStatement);
        return Response.status(Response.Status.CREATED).entity(annualStatement).build();
    }

    /**
     * Updates an existing annual statement.
     *
     * @param id              the ID of the annual statement to update
     * @param annualStatement the updated annual statement data
     * @return a Response containing the updated annual statement,
     * or a 404 Not Found status if the annual statement does not exist
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateAnnualStatement(@PathParam("id") long id, AnnualStatement annualStatement) {
        AnnualStatement existingAnnualStatement = annualStatementRepository.findById(id);
        if (existingAnnualStatement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (annualStatement.getRentalAgreement() != null) {
            existingAnnualStatement.setRentalAgreement(annualStatement.getRentalAgreement());
        }
        if (annualStatement.getPeriodStart() != null) {
            existingAnnualStatement.setPeriodStart(annualStatement.getPeriodStart());
        }
        if (annualStatement.getPeriodEnd() != null) {
            existingAnnualStatement.setPeriodEnd(annualStatement.getPeriodEnd());
        }
        if (annualStatement.getTotalCost() != 0.0) {
            existingAnnualStatement.setTotalCost(annualStatement.getTotalCost());
        }
        if (annualStatement.getTotalPrepayments() != 0.0) {
            existingAnnualStatement.setTotalPrepayments(annualStatement.getTotalPrepayments());
        }
        if (annualStatement.getDifference() != 0.0) {
            existingAnnualStatement.setDifference(annualStatement.getDifference());
        }

        Set<InvoiceCategory> categories = annualStatement.getInvoiceCategories();
        if (categories != null && !categories.isEmpty()) {
            for (InvoiceCategory category : categories) {
                InvoiceCategory existingCategory = invoiceCategoryRepository.findById(category.getInvoiceCategoryId());
                if (existingCategory != null) {
                    category = existingCategory;
                }
            }
        }
        existingAnnualStatement.setInvoiceCategories(categories);

        annualStatementRepository.persist(existingAnnualStatement);
        return Response.ok(existingAnnualStatement).build();
    }

    /**
     * Deletes an existing annual statement.
     *
     * @param id the ID of the annual statement to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the annual statement does not exist
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteAnnualStatement(@PathParam("id") long id) {
        AnnualStatement annualStatement = annualStatementRepository.findById(id);
        if (annualStatement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        annualStatementRepository.delete(annualStatement);
        return Response.noContent().build();
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */