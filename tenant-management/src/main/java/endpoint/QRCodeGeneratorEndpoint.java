/**
 * Start
 * @author 1 Zohal Mohammadi
 * @author 2 GitHub Copilot
 */
package endpoint;

import dto.PayableInvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.QRCodeGeneratorService;

/**
 * REST endpoint for handling QR code payment requests.
 */
@ApplicationScoped
@Path("/qr-code-generator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

/**
 * This declares the QRCodeGeneratorEndpoint class, which is a REST endpoint.
 */
public class QRCodeGeneratorEndpoint {

    /**
     * This initializes a logger for the class using SLF4J, which will be used to log messages
     */
    private static final Logger logger = LoggerFactory.getLogger(QRCodeGeneratorEndpoint.class);
    /**
     * This uses dependency injection to inject an instance of QRCodeGeneratorService into the endpoint. The QRCodeGeneratorService is likely responsible for generating QR codes.
     */
    @Inject
    QRCodeGeneratorService qrCodeGeneratorService;

    /**
     * Generates an EPC-QR code based on the provided PayableInvoiceDTO and returns it as a Base64 string.
     *
     * @param payableInvoiceDTO the invoice data transfer object containing the necessary information
     * @return a Response containing the Base64 string of the generated EPC-QR code
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateEpcQrCode(PayableInvoiceDTO payableInvoiceDTO) {
        try {
            logger.info("Received request to generate EPC-QR code: {}", payableInvoiceDTO);
            String base64Image = qrCodeGeneratorService.generateEpcQrCode(payableInvoiceDTO);
            return Response.ok(base64Image).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input for EPC-QR code: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("Error generating EPC-QR code: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while generating the EPC-QR code.").build();
        }
    }
}