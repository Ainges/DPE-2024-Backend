/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi, Moritz Baur
 */
package service;

import entity.AnnualStatement;
import entity.RentalAgreement;
import entity.StatementEntry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import repository.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Service for creating and managing annual statement PDF documents.
 */
@ApplicationScoped
public class AnnualStatementService {
    @Inject
    StatementEntryRepository statementEntryRepository;
    @Inject
    TenantRepository tenantRepository;


    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @Inject
    ApartmentRepository apartmentRepository;

    @Inject
    CreateStatementEntryService statementEntryService;

    @Inject
    InvoiceCategorySumService calculateInvoiceCategorySumService;

    @Inject
    AnnualStatementRepository annualStatementRepository;

    /**
     * Creates a new annual statement.
     *
     * @param annualStatement the annual statement to create
     * @return a Response containing the created annual statement
     */
    @Transactional
    public AnnualStatement createAnnualStatement(AnnualStatement annualStatement) {

        // Save the AnnualStatement
        annualStatementRepository.persist(annualStatement);
        // Return success response
        return annualStatement;
    }

    /**
     * Generates an annual statement for a whole year based on a rental agreement and a statement period.
     *
     * @param rentalAgreement       the rental agreement
     * @param annualStatementPeriod the statement period
     * @return the generated annual statement
     * @throws ParseException if the date parsing fails
     */
    public AnnualStatement generateAnnualStatementWholeYear(RentalAgreement rentalAgreement, String annualStatementPeriod) throws ParseException {
        if (annualStatementPeriod == null || annualStatementPeriod.isEmpty()) {
            throw new IllegalArgumentException("annualStatementPeriod must not be null or empty");
        }

        // 1. Create Annual Statement
        Date periodStart = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-01-01");
        Date periodEnd = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-12-31");

        // Parsing und Zuweisung
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);
        annualStatement.setPeriodStart(periodStart);
        annualStatement.setPeriodEnd(periodEnd);

        // Save the Annual Statement initially
        annualStatementRepository.persist(annualStatement);

        List<StatementEntry> statementEntries = statementEntryRepository
                .find("rentalAgreement.rentalAgreementId = ?1 and annualStatementPeriod = ?2",
                        rentalAgreement.getRentalAgreementId(),
                        annualStatementPeriod)
                .list();

        // Calculate prepayments
        float heatingCostPrepayment = rentalAgreement.getApartment().getHeatingCostPrepayment();
        float additionalCostPrepayment = rentalAgreement.getApartment().getAdditionalCostPrepayment();
        float totalPrepayments = (heatingCostPrepayment + additionalCostPrepayment) * 12;

        // 3. Sum the costs from the StatementEntries
        float totalCost = 0.0f;
        for (StatementEntry statementEntry : statementEntries) {
            totalCost += statementEntry.getAmountPayable();
            statementEntry.setAnnualStatement(annualStatement); // Link with Annual Statement
            statementEntryRepository.persist(statementEntry);   // Update StatementEntry
        }

        // 4. Calculate the difference (overallAmountPayable)
        float difference = totalCost - totalPrepayments;

        // 5. Update Annual Statement
        annualStatement.setTotalCost(totalCost);
        annualStatement.setTotalPrepayments(totalPrepayments);
        annualStatement.setDifference(difference);

        // Save the Updated Annual Statement
        annualStatementRepository.persist(annualStatement);
        return annualStatement;
    }

    /**
     * Generates an annual statement for a mid-year period based on a rental agreement and a start and end date of the statement period.
     *
     * @param rentalAgreement       the rental agreement
     * @param annualStatementPeriod the statement period
     * @return the generated annual statement
     * @throws ParseException if the date parsing fails
     */
    public AnnualStatement generateAnnualStatementMidYear(RentalAgreement rentalAgreement, String annualStatementPeriod) throws ParseException {
        // 1. Create MidYearAnnual Statement
        // Entfernen des Zeitstempels, falls vorhanden
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);

        Date periodStart = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-01-01");
        Date periodEnd = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-12-31");

        Date rentalStartDate = rentalAgreement.getStartDate();
        Date rentalEndDate = rentalAgreement.getEndDate();

        if (rentalStartDate.after(periodStart) && rentalStartDate.before(periodEnd)) {
            periodStart = rentalStartDate;
        }

        if ((rentalEndDate != null) && rentalEndDate.after(periodStart) && rentalEndDate.before(periodEnd)) {
            periodEnd = rentalEndDate;
        }

        annualStatement.setPeriodStart(periodStart);
        annualStatement.setPeriodEnd(periodEnd);

        // Save the Annual Statement initially
        annualStatementRepository.persist(annualStatement);

        List<StatementEntry> statementEntries = statementEntryRepository
                .find("rentalAgreement.rentalAgreementId = ?1 and annualStatementPeriod = ?2",
                        rentalAgreement.getRentalAgreementId(),
                        annualStatementPeriod)
                .list();

        // Berechnung der Gesamtkosten
        float totalCost = 0.0f;
        for (StatementEntry statementEntry : statementEntries) {
            totalCost += statementEntry.getAmountPayable();
            statementEntry.setAnnualStatement(annualStatement); // Verknüpfung mit Annual Statement
            statementEntryRepository.persist(statementEntry);   // Aktualisierung des StatementEntry
        }
        // Vorauszahlungen berechnen (proportional zur Mietdauer)

        long months = ChronoUnit.MONTHS.between(
                periodStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                periodEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        float heatingCostPrepayment = rentalAgreement.getApartment().getHeatingCostPrepayment();
        float additionalCostPrepayment = rentalAgreement.getApartment().getAdditionalCostPrepayment();
        float totalPrepayments = (heatingCostPrepayment + additionalCostPrepayment) * months;

        // Differenz berechnen
        float difference = totalCost - totalPrepayments;
        // 5. Update Annual Statement
        annualStatement.setTotalCost(totalCost);
        annualStatement.setTotalPrepayments(totalPrepayments);
        annualStatement.setDifference(difference);

        // Save the Updated Annual Statement
        annualStatementRepository.persist(annualStatement);
        return annualStatement;
    }

    /**
     * Creates a PDF document for an annual statement based on the ID of the annual statement.
     *
     * @param annualStatementId the ID of the annual statement
     * @return the PDF document as a byte array
     * @throws IOException if an I/O error occurs
     */
    public String createPDF(long annualStatementId) throws IOException {
        AnnualStatement annualStatement = annualStatementRepository.findById(annualStatementId);
        if (annualStatement == null) {
            throw new IllegalArgumentException("AnnualStatement with ID " + annualStatementId + " not found.");
        }

        RentalAgreement rentalAgreement = annualStatement.getRentalAgreement();
        List<StatementEntry> statementEntries = statementEntryRepository
                .find("rentalAgreement.rentalAgreementId = ?1 and annualStatement.annualStatementId = ?2",
                        rentalAgreement.getRentalAgreementId(), annualStatementId)
                .list();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50; // Margin for the page
                float yStart = 750; // Starting Y position
                float yPosition = yStart;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 15; // Reduce row height to fit more rows
                float[] columnWidths = {tableWidth * 0.5f, tableWidth * 0.25f, tableWidth * 0.25f}; // Column proportions

                // Header text
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Annual Statement");
                contentStream.endText();
                yPosition -= 30; // Space after the header

                // Rental Agreement details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Houseing Object: " + annualStatement.getRentalAgreement().getApartment().getHousingObject().getName());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Apartment: " + annualStatement.getRentalAgreement().getApartment().getApartmentId());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Apartment Area in m²: " + annualStatement.getRentalAgreement().getApartment().getAreaInM2());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period Start: " + new SimpleDateFormat("dd-MM-yyyy").format(annualStatement.getPeriodStart()));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period End: " + new SimpleDateFormat("dd-MM-yyyy").format(annualStatement.getPeriodEnd()));
                contentStream.endText();
                yPosition -= 75; // Space after the details

                // Draw table headers
                drawTableHeaders(contentStream, new String[]{"Statement Entry", "Overall Amount", "Amount Payable"}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;

                // Draw horizontal line below headers
                drawHorizontalLine(contentStream, margin, yPosition + rowHeight - 5, tableWidth);
                yPosition -= 5;

                // Draw table rows
                for (StatementEntry entry : statementEntries) {
                    // Draw the current row
                    drawTableRow(contentStream, new String[]{
                            entry.getName(),
                            String.format("%.2f", entry.getOverallAmount()),
                            String.format("%.2f", entry.getAmountPayable())
                    }, columnWidths, margin, yPosition);

                    // Draw horizontal line for each row
                    yPosition -= rowHeight;
                    drawHorizontalLine(contentStream, margin, yPosition, tableWidth);

                }

                // Space before totals
                yPosition -= rowHeight;

                // Draw the totals
                drawTableRow(contentStream, new String[]{"Total Cost", "", String.format("%.2f", annualStatement.getTotalCost())}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;
                drawTableRow(contentStream, new String[]{"Total Prepayments", "", String.format("%.2f", annualStatement.getTotalPrepayments())}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;
                drawTableRow(contentStream, new String[]{"Difference", "", String.format("%.2f", annualStatement.getDifference())}, columnWidths, margin, yPosition);

                // Final horizontal line below totals
                drawHorizontalLine(contentStream, margin, yPosition - 5, tableWidth);

                contentStream.close(); // Close the final content stream
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            // Save the PDF to the file system
            Path pdfPath = Paths.get("AnnualStatement_" + annualStatementId + ".pdf");
            Files.write(pdfPath, out.toByteArray());

            // Convert the PDF to a Base64 string with annotation
            return "data:application/pdf;base64," + Base64.getEncoder().encodeToString(out.toByteArray());
        }
    }

    // Helper method to draw a horizontal line
    private void drawHorizontalLine(PDPageContentStream contentStream, float startX, float startY, float width) throws IOException {
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(startX + width, startY);
        contentStream.stroke();
    }

    // Helper method to draw table headers
    private void drawTableHeaders(PDPageContentStream contentStream, String[] headers, float[] columnWidths, float margin, float yPosition) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float xPosition = margin;
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(headers[i]);
            contentStream.endText();
            xPosition += columnWidths[i];
        }
    }

    // Helper method to draw a single table row
    private void drawTableRow(PDPageContentStream contentStream, String[] values, float[] columnWidths, float margin, float yPosition) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        float xPosition = margin;
        for (int i = 0; i < values.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(values[i]);
            contentStream.endText();
            xPosition += columnWidths[i];
        }
    }

}
/**
 * End
 *
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi, Moritz Baur
 */