/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package endpoint;

import entity.AnnualStatement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.AnnualStatementRepository;

import java.util.List;

@ApplicationScoped
@Path("/annual-statements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnualStatementEndpoint {

    @Inject
    AnnualStatementRepository annualStatementRepository;

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