package endpoint;

import dto.CreateStatementEntryServiceDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.AnnualStatementDocumentCreationService;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
@Path("/pdf-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnualStatementDocumentEndpoint {

    @Inject
    AnnualStatementDocumentCreationService annualStatementDocumentCreationService;

    @POST
    @Path("/generate")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePDF(List<CreateStatementEntryServiceDTO> dtos) {
        try {
            byte[] pdfData = annualStatementDocumentCreationService.generatePDF(dtos);
            return Response.ok(pdfData)
                    .header("Content-Disposition", "attachment; filename=AnnualStatement.pdf")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response updatePDF(@PathParam("id") Long id, CreateStatementEntryServiceDTO dto) {
        try {
            byte[] pdfData = annualStatementDocumentCreationService.updatePDF(id, dto);
            return Response.ok(pdfData)
                    .header("Content-Disposition", "attachment; filename=UpdatedAnnualStatement.pdf")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deletePDF(@PathParam("id") Long id) {
        try {
            annualStatementDocumentCreationService.deletePDF(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPDF(@PathParam("id") Long id) {
        try {
            byte[] pdfData = annualStatementDocumentCreationService.getPDF(id);
            return Response.ok(pdfData)
                    .header("Content-Disposition", "attachment; filename=AnnualStatement.pdf")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */