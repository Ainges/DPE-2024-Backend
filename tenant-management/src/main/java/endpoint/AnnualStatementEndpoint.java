/**
 * @author 1 Zohal Mohammadi, Moritz Baur
 * @author 2 GitHub Copilot
 */

package endpoint;

import dto.AnnualStatementDTO;
import entity.AnnualStatement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.AnnualStatementRepository;
import service.AnnualStatementService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Defines a JAX-RS REST endpoint class AnnualStatementEndpoint
 * The class is annotated with @ApplicationScoped, @Path, @Produces, and @Consumes to specify its scope, base path, and media types for request and response bodies
 */
@ApplicationScoped
@Path("/annual-statements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnualStatementEndpoint {

    /**
     * The AnnualStatementRepository and AnnualStatementService are being injected into the AnnualStatementEndpoint class.
     * This means that instances of these classes will be provided by the dependency injection framework (in this case, Jakarta EE) when an instance of AnnualStatementEndpoint is created. T
     * this allows the AnnualStatementEndpoint to use these services without having to instantiate them directly, promoting loose coupling and easier testing
     */
    @Inject
    AnnualStatementRepository annualStatementRepository;
    @Inject
    AnnualStatementService annualStatementService;


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
     * Creates a new annual statement entry in the database.
     *
     * @param annualStatement the annual statement to create
     * @return a Response containing the created annual statement
     */
    @POST
    @Transactional
    public Response createAnnualStatement(AnnualStatement annualStatement) {
        AnnualStatement createdAnnualStatement = annualStatementService.createAnnualStatement(annualStatement);
        return Response.status(Response.Status.CREATED).entity(createdAnnualStatement).build();
    }

    /**
     * Generates an annual statement for the whole year.
     *
     * @param dto the data transfer object containing rental agreement and annual statement period
     * @return a Response containing the created annual statement
     * @throws ParseException if there is an error parsing the date
     */
    @POST
    @Path("/wholeYear")
    @Transactional
    public Response generateAnnualStatementWholeYear(AnnualStatementDTO dto) throws ParseException {
        AnnualStatement createdAnnualStatement = annualStatementService.generateAnnualStatementWholeYear(dto.getRentalAgreement(), dto.getAnnualStatementPeriod());
        return Response.status(Response.Status.CREATED).entity(createdAnnualStatement).build();
    }

    /**
     * Generates an annual statement for mid-year tenant changes.
     *
     * @param dto the data transfer object containing rental agreement and annual statement period
     * @return a Response containing the created annual statement
     * @throws ParseException if there is an error parsing the date
     */
    @POST
    @Path("/midYear")
    @Transactional
    public Response generateAnnualStatementMidYear(AnnualStatementDTO dto) throws ParseException {
        AnnualStatement createdAnnualStatement = annualStatementService.generateAnnualStatementMidYear(dto.getRentalAgreement(), dto.getAnnualStatementPeriod());
        return Response.status(Response.Status.CREATED).entity(createdAnnualStatement).build();
    }

    /**
     * Generates a PDF for the specified annual statement and returns it as a Base64 string.
     *
     * @param id the ID of the annual statement
     * @return a Response containing the Base64 string of the generated PDF
     */
    @GET
    @Path("/{id}/pdf")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPDF(@PathParam("id") long id) {
        try {
            String base64Pdf = annualStatementService.createPDF(id);
            return Response.ok(base64Pdf).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
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