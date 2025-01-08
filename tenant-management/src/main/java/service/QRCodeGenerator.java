package service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import dto.InvoiceDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Formatter;

@ApplicationScoped
public class QRCodeGenerator {

    /**
     * Generates an EPC-QR Code for bank transfers based on the given InvoiceDTO and saves it to the file system.
     *
     * @param invoiceDTO the invoice data transfer object containing the necessary information
     * @param filePath the file path where the QR code should be saved
     * @throws Exception if an error occurs during QR code generation
     */
    public void generateEpcQrCode(InvoiceDTO invoiceDTO, String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        // EPC-QR Code format
        formatter.format("BCD\n");
        formatter.format("001\n");
        formatter.format("1\n");
        formatter.format("SCT\n");
        formatter.format("%s\n", invoiceDTO.getBic() != null ? invoiceDTO.getBic() : "");
        formatter.format("%s\n", invoiceDTO.getReceiver());
        formatter.format("%s\n", invoiceDTO.getReceiverIban());
        formatter.format("%s\n", invoiceDTO.getCurrency());
        formatter.format("%.2f\n", invoiceDTO.getInvoiceAmount());
        formatter.format("\n"); // Purpose of the payment (optional)
        formatter.format("%s\n", invoiceDTO.getReference() != null ? invoiceDTO.getReference() : "");
        formatter.format("%s\n", invoiceDTO.getDescription());

        String epcQrCodeData = sb.toString();

        // Generate QR code image
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(epcQrCodeData, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException("Error generating QR code: " + e.getMessage(), e);
        }

        Path path = FileSystems.getDefault().getPath(filePath);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (IOException e) {
            throw new RuntimeException("Error saving QR code to file: " + e.getMessage(), e);
        }
    }
}