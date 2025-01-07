package service;

import dto.CreateStatementEntryServiceDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import repository.StatementEntryRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for creating and managing annual statement PDF documents.
 */
@ApplicationScoped
public class AnnualStatementDocumentCreationService {
    @Inject
    StatementEntryRepository statementEntryRepository;

    private Map<Long, byte[]> pdfStorage = new HashMap<>();
    private long currentId = 1;

    /**
     * Generates a PDF document for the given list of statement entries.
     *
     * @param dtos List of CreateStatementEntryServiceDTO objects containing the data for the PDF.
     * @return A byte array representing the generated PDF document.
     * @throws IOException If an I/O error occurs during PDF generation.
     */
    public byte[] generatePDF(List<CreateStatementEntryServiceDTO> dtos) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Annual Statement");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                for (CreateStatementEntryServiceDTO dto : dtos) {
                    contentStream.showText("Distribution Key: " + dto.getDistributionKey());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Period Start: " + dto.getPeriodStart());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Period End: " + dto.getPeriodEnd());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Total Cost: " + dto.getTotalCost());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Total Prepayments: " + dto.getTotalPrepayments());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Difference: " + dto.getDifference());
                    contentStream.newLineAtOffset(0, -30); // Add some space between entries
                }

                contentStream.endText();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            byte[] pdfData = out.toByteArray();

            long id = currentId++;
            pdfStorage.put(id, pdfData);

            return pdfData;
        }
    }

    /**
     * Updates an existing PDF document with new data.
     *
     * @param id  The ID of the PDF document to update.
     * @param dto The new data to include in the PDF.
     * @return A byte array representing the updated PDF document.
     * @throws IOException If an I/O error occurs during PDF generation.
     */
    public byte[] updatePDF(Long id, CreateStatementEntryServiceDTO dto) throws IOException {
        if (!pdfStorage.containsKey(id)) {
            throw new IllegalArgumentException("PDF not found");
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Updated Annual Statement");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Period Start: " + dto.getPeriodStart());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period End: " + dto.getPeriodEnd());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Total Cost: " + dto.getTotalCost());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Total Prepayments: " + dto.getTotalPrepayments());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Difference: " + dto.getDifference());
                contentStream.endText();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            byte[] pdfData = out.toByteArray();

            pdfStorage.put(id, pdfData);

            return pdfData;
        }
    }

    /**
     * Deletes a PDF document by its ID.
     *
     * @param id The ID of the PDF document to delete.
     */
    public void deletePDF(Long id) {
        if (!pdfStorage.containsKey(id)) {
            throw new IllegalArgumentException("PDF not found");
        }

        pdfStorage.remove(id);
    }

    /**
     * Retrieves a PDF document by its ID.
     *
     * @param id The ID of the PDF document to retrieve.
     * @return A byte array representing the PDF document.
     */
    public byte[] getPDF(Long id) {
        if (!pdfStorage.containsKey(id)) {
            throw new IllegalArgumentException("PDF not found");
        }

        return pdfStorage.get(id);
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */