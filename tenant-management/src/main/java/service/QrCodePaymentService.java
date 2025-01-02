package service;

import com.github.mateuszjanczak.paymentqrcode.PaymentQRCode;
import com.github.mateuszjanczak.paymentqrcode.exceptions.WrongInputException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dto.InvoiceDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@ApplicationScoped
public class QrCodePaymentService {
    private static final Logger logger = LoggerFactory.getLogger(QrCodePaymentService.class);
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public String generateQrCode(InvoiceDTO invoiceDTO) {
        try {
            logger.info("Generating QR code for invoice: {}", invoiceDTO);
            PaymentQRCode paymentQRCode = PaymentQRCode.Builder
                    .paymentQRCode()
                    .withRecipient(invoiceDTO.getReceiver())
                    .withAccountNumber(invoiceDTO.getReceiverIban())
                    .withAmount(invoiceDTO.getInvoiceAmount())
                    .withTitle(invoiceDTO.getDescription())
                    .withCountry("DE")
                    .build();

            generateQRCodeImage(paymentQRCode.getQRCodeSubject(), 250, 250, QR_CODE_IMAGE_PATH);
            return QR_CODE_IMAGE_PATH;
        } catch (WrongInputException e) {
            logger.error("Invalid input for PaymentQRCode: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid input for PaymentQRCode: " + e.getMessage(), e);
        } catch (WriterException | IOException e) {
            logger.error("Error generating QR code: {}", e.getMessage());
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