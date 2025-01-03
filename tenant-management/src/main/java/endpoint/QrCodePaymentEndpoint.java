package endpoint;

import dto.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class QrCodePaymentEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(QrCodePaymentEndpoint.class);

    @Inject
    QrCodePaymentService qrCodePaymentService;

    @POST
    @Path("/generate")
    @Produces("image/png")
    public Response generateQrCode(InvoiceDTO invoiceDTO) {
        try {
            logger.info("Received request to generate QR code: {}", invoiceDTO);
            String qrCodePath = qrCodePaymentService.generateQrCode(invoiceDTO);
            File qrCodeFile = new File(qrCodePath);
            InputStream qrCodeStream = new FileInputStream(qrCodeFile);
            return Response.ok(qrCodeStream).type("image/png").build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for PaymentQRCode: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (IOException e) {
            logger.error("Error reading QR code file: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while reading the QR code file.").build();
        } catch (Exception e) {
            logger.error("Error generating QR code: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while generating the QR code.").build();
        }
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */