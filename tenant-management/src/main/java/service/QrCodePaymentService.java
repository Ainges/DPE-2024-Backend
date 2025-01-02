package service;

import com.github.mateuszjanczak.paymentqrcode.PaymentQRCode;
import com.github.mateuszjanczak.paymentqrcode.exceptions.WrongInputException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.Invoice;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@ApplicationScoped
public class QrCodePaymentService {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public String generateQrCode(Invoice invoice) {
        try {
            PaymentQRCode paymentQRCode = PaymentQRCode.Builder
                    .paymentQRCode()
                    .withRecipient(invoice.getReceiver())
                    .withAccountNumber(invoice.getReceiverIban())
                    .withAmount(invoice.getInvoiceAmount())
                    .withTitle(invoice.getDescription())
                    .withCountry("DE")
                    .build();

            generateQRCodeImage(paymentQRCode.getQRCodeSubject(), 250, 250, QR_CODE_IMAGE_PATH);
            return QR_CODE_IMAGE_PATH; // Added return statement
        } catch (WrongInputException e) {
            throw new IllegalArgumentException("Invalid input for PaymentQRCode: " + e.getMessage(), e);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating QR code: " + e.getMessage(), e);
        }
    }

    private void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */