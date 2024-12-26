/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
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

    @GET
    public List<AnnualStatement> getAllAnnualStatements() {
        return annualStatementRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public AnnualStatement getAnnualStatement(@PathParam("id") long id) {
        return annualStatementRepository.findById(id);
    }

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

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateAnnualStatement(@PathParam("id") long id, AnnualStatement annualStatement) {
        AnnualStatement existingAnnualStatement = annualStatementRepository.findById(id);
        if (existingAnnualStatement == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingAnnualStatement.setRentalAgreement(annualStatement.getRentalAgreement());
        existingAnnualStatement.setPeriodStart(annualStatement.getPeriodStart());
        existingAnnualStatement.setPeriodEnd(annualStatement.getPeriodEnd());
        existingAnnualStatement.setTotalCost(annualStatement.getTotalCost());
        existingAnnualStatement.setTotalPrepayments(annualStatement.getTotalPrepayments());
        existingAnnualStatement.setDifference(annualStatement.getDifference());

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
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */