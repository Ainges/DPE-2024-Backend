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
//defines a JAX-RS REST endpoint in a Spring Boot application.

//@ApplicationScoped: Indicates that the class is a CDI (Contexts and Dependency Injection) bean with application scope, meaning it will be instantiated once and shared across the application.
//@Path("/statement-entries"): Specifies the base URI path for the REST endpoint.
//@Produces(MediaType.APPLICATION_JSON): Indicates that the endpoint will produce responses in JSON format.
//@Consumes(MediaType.APPLICATION_JSON): Indicates that the endpoint will consume requests in JSON format.


@ApplicationScoped
@Path("/statement-entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//public class StatementEntryEndpoint: Declares the class as a public class named StatementEntryEndpoint.
public class StatementEntryEndpoint {
    //@Inject StatementEntryRepository statementEntryRepository: Injects an instance of StatementEntryRepository into the endpoint, allowing it to interact with the data repository for StatementEntry entities.
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