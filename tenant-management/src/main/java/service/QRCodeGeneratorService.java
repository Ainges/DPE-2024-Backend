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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * StringBuilder and Formatter Initialization:
     * A StringBuilder object sb is created to build the EPC-QR code data string.
     * A Formatter object formatter is created using the StringBuilder to format the data.
     * EPC-QR Code Format:
     * formatter formats and appends various pieces of information to the StringBuilder according to the EPC-QR code specification:
     * "BCD\n": EPC-QR code identifier.
     * "001\n": Version of the EPC-QR code.
     * "1\n": Character set (UTF-8).
     * "SCT\n": Service code for SEPA Credit Transfer.
     * payableInvoiceDTO.getBic(): BIC (Bank Identifier Code) of the receiver's bank.
     * payableInvoiceDTO.getReceiver(): Name of the receiver.
     * payableInvoiceDTO.getReceiverIban(): IBAN (International Bank Account Number) of the receiver.
     * payableInvoiceDTO.getCurrency() + payableInvoiceDTO.getInvoiceAmount(): Currency and amount of the invoice.
     * payableInvoiceDTO.getDescription(): Description of the payment (optional).
     * "Tenant-Management-System\n": Additional information (optional).
     * EPC-QR Code Data String:
     * The formatted data is converted to a string and stored in epcQrCodeData.
     */
    public String generateEpcQrCode(PayableInvoiceDTO payableInvoiceDTO) {
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

        /**
         * Generate QR code image
         */
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(epcQrCodeData, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException("Error generating QR code: " + e.getMessage(), e);
        }

        /**
         *  Write the QR code to a ByteArrayOutputStream
         */
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error writing QR code to output stream: " + e.getMessage(), e);
        }

        /**
         * Save the QR code to the file system
         */
        Path qrCodePath = Paths.get("QRCode_" + payableInvoiceDTO.getReceiverIban() +"_"+payableInvoiceDTO.getInvoiceAmount() + ".png");
        try (FileOutputStream fos = new FileOutputStream(qrCodePath.toFile())) {
            fos.write(pngOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error saving QR code to file system: " + e.getMessage(), e);
        }

        /**
         * Convert the ByteArrayOutputStream to a Base64 string
         */
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