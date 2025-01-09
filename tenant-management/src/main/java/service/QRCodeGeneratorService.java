/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi, Moritz Baur
 */
package service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import dto.PayableInvoiceDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Formatter;

/**
 * Service class for generating EPC-QR codes.
 */
@ApplicationScoped
public class QRCodeGeneratorService {

    /**
     * Generates an EPC-QR Code for bank transfers based on the given PayableInvoiceDTO and returns it as a Base64 string.
     *
     * @param payableInvoiceDTO the invoice data transfer object containing the necessary information
     * @return a Base64 string of the generated EPC-QR code
     * @throws Exception if an error occurs during QR code generation
     */
    public String generateEpcQrCode(PayableInvoiceDTO payableInvoiceDTO) throws Exception {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        // EPC-QR Code format
        formatter.format("BCD\n");
        formatter.format("001\n");
        formatter.format("1\n");
        formatter.format("SCT\n");
        formatter.format("%s\n", payableInvoiceDTO.getBic() != null ? payableInvoiceDTO.getBic() : "");
        formatter.format("%s\n", payableInvoiceDTO.getReceiver());
        formatter.format("%s\n", payableInvoiceDTO.getReceiverIban());
        formatter.format("%s%.2f\n", payableInvoiceDTO.getCurrency(), payableInvoiceDTO.getInvoiceAmount());
        formatter.format("\n"); // Purpose of the payment (optional)
        formatter.format("%s\n", payableInvoiceDTO.getDescription() != null ? payableInvoiceDTO.getDescription() : "");
        formatter.format("\n"); // Description of the payment (optional)
        formatter.format("Tenant-Management-System\n");

        String epcQrCodeData = sb.toString();

        // Generate QR code image
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(epcQrCodeData, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException("Error generating QR code: " + e.getMessage(), e);
        }

        // Write the QR code to a ByteArrayOutputStream
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error writing QR code to output stream: " + e.getMessage(), e);
        }

        // Convert the ByteArrayOutputStream to a Base64 string
        byte[] pngData = pngOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(pngData);
    }
}
/**
 * End
 *
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi, Moritz Baur
 */