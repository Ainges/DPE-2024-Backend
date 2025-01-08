/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.StatementEntry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.StatementEntryRepository;

import java.util.List;

@ApplicationScoped
@Path("/statement-entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatementEntryEndpoint {

    @Inject
    StatementEntryRepository statementEntryRepository;

    /**
     * Retrieves all StatementEntry entities.
     *
     * @return a list of all StatementEntry entities
     */
    @GET
    public List<StatementEntry> getAllStatementEntries() {
        return statementEntryRepository.listAll();
    }

    /**
     * Retrieves a specific StatementEntry entity by its ID.
     *
     * @param id the ID of the StatementEntry entity to retrieve
     * @return the StatementEntry entity with the specified ID
     */
    @GET
    @Path("/{id}")
    public StatementEntry getStatementEntry(@PathParam("id") long id) {
        return statementEntryRepository.findById(id);
    }

    /**
     * Creates a new StatementEntry entity.
     *
     * @param statementEntry the StatementEntry entity to create
     * @return a Response indicating the outcome of the create operation
     */
    @POST
    @Transactional
    public Response createStatementEntry(StatementEntry statementEntry) {
        statementEntryRepository.persist(statementEntry);
        return Response.status(Response.Status.CREATED).entity(statementEntry).build();
    }

    /**
     * Updates an existing StatementEntry entity.
     *
     * @param id             the ID of the StatementEntry entity to update
     * @param statementEntry the updated StatementEntry entity
     * @return a Response indicating the outcome of the update operation
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateStatementEntry(@PathParam("id") long id, StatementEntry statementEntry) {
        StatementEntry existingStatementEntry = statementEntryRepository.findById(id);
        if (existingStatementEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (statementEntry.getName() != null) {
            existingStatementEntry.setName(statementEntry.getName());
        }
        if (statementEntry.getOverallAmount() != 0.0) {
            existingStatementEntry.setOverallAmount(statementEntry.getOverallAmount());
        }
        if (statementEntry.getAmountPayable() != 0.0) {
            existingStatementEntry.setAmountPayable(statementEntry.getAmountPayable());
        }
        if (statementEntry.getDistributionKey() != null) {
            existingStatementEntry.setDistributionKey(statementEntry.getDistributionKey());
        }
        if (statementEntry.getRentalAgreement() != null) {
            existingStatementEntry.setRentalAgreement(statementEntry.getRentalAgreement());
        }
        if (statementEntry.getAnnualStatement() != null) {
            existingStatementEntry.setAnnualStatement(statementEntry.getAnnualStatement());
        }
        if (statementEntry.getAnnualStatementPeriod() != null) {
            existingStatementEntry.setAnnualStatementPeriod(statementEntry.getAnnualStatementPeriod());
        }
        statementEntryRepository.persist(existingStatementEntry);
        return Response.ok(existingStatementEntry).build();
    }

    /**
     * Deletes a specific StatementEntry entity by its ID.
     *
     * @param id the ID of the StatementEntry entity to delete
     * @return a Response indicating the outcome of the delete operation
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteStatementEntry(@PathParam("id") long id) {
        StatementEntry statementEntry = statementEntryRepository.findById(id);
        if (statementEntry == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        statementEntryRepository.delete(statementEntry);
        return Response.noContent().build();
    }
}
/**
 * End
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */