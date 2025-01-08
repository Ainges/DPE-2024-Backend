/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur, Zohal Mohammadi
 */
package endpoint;

import dto.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.QRCodeGenerator;
import service.QrCodePaymentService;

import java.io.*;

/**
 * REST endpoint for handling QR code payment requests.
 */
@ApplicationScoped
@Path("/qrCodePayment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QrCodePaymentEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(QrCodePaymentEndpoint.class);

    @Inject
    QrCodePaymentService qrCodePaymentService;

    @Inject
    QRCodeGenerator qrCodeGenerator;

    /**
     * Generates a QR code based on the provided InvoiceDTO and returns it as a PNG image.
     *
     * @param invoiceDTO the invoice data transfer object containing the necessary information
     * @return a Response containing the QR code image
     */
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

    /**
     * Generates an EPC-QR code based on the provided InvoiceDTO and saves it to the file system.
     *
     * @param invoiceDTO the invoice data transfer object containing the necessary information
     * @return a Response containing the file path of the saved EPC-QR code
     */
    @POST
    @Path("/generateEpcQrCode")
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateEpcQrCode(InvoiceDTO invoiceDTO) {
        try {
            logger.info("Received request to generate EPC-QR code: {}", invoiceDTO);
            String filePath = "./EpcQrCode.png";
            qrCodeGenerator.generateEpcQrCode(invoiceDTO, filePath);
            return Response.ok(filePath).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for EPC-QR code: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("Error generating EPC-QR code: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while generating the EPC-QR code.").build();
        }
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur, Zohal Mohammadi
 */