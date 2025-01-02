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
     * Retrieves all statement entries.
     *
     * @return a list of all statement entries
     */
    @GET
    public List<StatementEntry> getAllStatementEntries() {
        return statementEntryRepository.listAll();
    }

    /**
     * Retrieves a specific statement entry by its ID.
     *
     * @param id the ID of the statement entry
     * @return the statement entry with the specified ID
     */
    @GET
    @Path("/{id}")
    public StatementEntry getStatementEntry(@PathParam("id") long id) {
        return statementEntryRepository.findById(id);
    }

    /**
     * Creates a new statement entry.
     *
     * @param statementEntry the statement entry to create
     * @return a Response containing the created statement entry
     */
    @POST
    @Transactional
    public Response createStatementEntry(StatementEntry statementEntry) {
        statementEntryRepository.persist(statementEntry);
        return Response.status(Response.Status.CREATED).entity(statementEntry).build();
    }

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
        statementEntryRepository.persist(existingStatementEntry);
        return Response.ok(existingStatementEntry).build();
    }

    /**
     * Deletes an existing statement entry.
     *
     * @param id the ID of the statement entry to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the statement entry does not exist
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