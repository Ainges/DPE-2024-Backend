package endpoint;

import entity.Invoice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.QrCodePaymentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
@Path("/qrCodePayment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QrCodePaymentEndpoint {

    @Inject
    QrCodePaymentService qrCodePaymentService;

    @POST
    @Path("/generate")
    @Produces("image/png")
    public Response generateQrCode(Invoice invoice) {
        try {
            String qrCodePath = qrCodePaymentService.generateQrCode(invoice);
            File qrCodeFile = new File(qrCodePath);
            InputStream qrCodeStream = new FileInputStream(qrCodeFile);
            return Response.ok(qrCodeStream).type("image/png").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while reading the QR code file.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while generating the QR code.").build();
        }
    }
}