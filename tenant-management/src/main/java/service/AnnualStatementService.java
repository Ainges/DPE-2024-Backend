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
import repository.AnnualStatementRepository;
import repository.ApartmentRepository;
import repository.RentalAgreementRepository;
import repository.StatementEntryRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service for creating and managing annual statement PDF documents.
 */
@ApplicationScoped
public class AnnualStatementService {
    @Inject
    StatementEntryRepository statementEntryRepository;

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
     * @param rentalAgreement the rental agreement
     * @param annualStatementPeriod the statement period
     * @return the generated annual statement
     * @throws ParseException if the date parsing fails
     */
    public AnnualStatement generateAnnualStatementWholeYear(RentalAgreement rentalAgreement, String annualStatementPeriod) throws ParseException {
        if (annualStatementPeriod == null || annualStatementPeriod.isEmpty()) {
            throw new IllegalArgumentException("annualStatementPeriod must not be null or empty");
        }

        // 1. Create Annual Statement
        String periodStart = (annualStatementPeriod + "-01-01").split("T")[0];
        String periodEnd = (annualStatementPeriod + "-12-31").split("T")[0];
        // Parsing und Zuweisung
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);
        annualStatement.setPeriodStart(new SimpleDateFormat("yyyy-MM-dd").parse(periodStart));
        annualStatement.setPeriodEnd(new SimpleDateFormat("yyyy-MM-dd").parse(periodEnd));

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
     * @param rentalAgreement the rental agreement
     * @param periodStart the start date of the period
     * @param periodEnd the end date of the period
     * @return the generated annual statement
     * @throws ParseException if the date parsing fails
     */
    public AnnualStatement generateAnnualStatementMidYear(RentalAgreement rentalAgreement, String periodStart, String periodEnd) throws ParseException {
        // 1. Create MidYearAnnual Statement
        // Entfernen des Zeitstempels, falls vorhanden
        periodStart = periodStart.split("T")[0]; // Nur das Datum vor "T" wird verwendet
        periodEnd = periodEnd.split("T")[0];     // Nur das Datum vor "T" wird verwende
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);
        annualStatement.setPeriodStart(new SimpleDateFormat("yyyy-MM-dd").parse(periodStart));
        annualStatement.setPeriodEnd(new SimpleDateFormat("yyyy-MM-dd").parse(periodEnd));

        // Save the Annual Statement initially
        annualStatementRepository.persist(annualStatement);
        List<StatementEntry> statementEntries = statementEntryRepository
                .find("rentalAgreement.rentalAgreementId = ?1 and annualStatement.periodStart >= ?2 and annualStatement.periodEnd <= ?3",
                        rentalAgreement.getRentalAgreementId(),
                        annualStatement.getPeriodStart(),
                        annualStatement.getPeriodEnd())
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
                annualStatement.getPeriodStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                annualStatement.getPeriodEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
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
    public byte[] createPDF(long annualStatementId) throws IOException {
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
                float[] columnWidths = {tableWidth * 0.7f, tableWidth * 0.3f}; // Column proportions

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
                contentStream.showText("Rental Agreement ID: " + rentalAgreement.getRentalAgreementId());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period Start: " + annualStatement.getPeriodStart());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period End: " + annualStatement.getPeriodEnd());
                contentStream.endText();
                yPosition -= 45; // Space after the details

                // Draw table headers
                drawTableHeaders(contentStream, new String[]{"Statement Entry", "Amount Payable"}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;

                // Draw table rows
                float totalCost = 0.0f;
                for (StatementEntry entry : statementEntries) {
                    // Check if we are running out of space
                    if (yPosition < margin + 3 * rowHeight) { // Reserve space for totals
                        break; // Prevent drawing further rows and leave room for totals
                    }

                    // Draw the current row
                    drawTableRow(contentStream, new String[]{
                            entry.getName(),
                            String.format("%.2f", entry.getAmountPayable())
                    }, columnWidths, margin, yPosition);

                    // Sum up the total cost
                    totalCost += entry.getAmountPayable();
                    yPosition -= rowHeight; // Move to the next row position
                }

                // Draw the totals
                yPosition -= rowHeight; // Space before totals
                drawTableRow(contentStream, new String[]{"Total Cost", String.format("%.2f", totalCost)}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;
                drawTableRow(contentStream, new String[]{"Total Prepayments", String.format("%.2f", annualStatement.getTotalPrepayments())}, columnWidths, margin, yPosition);
                yPosition -= rowHeight;
                drawTableRow(contentStream, new String[]{"Difference", String.format("%.2f", annualStatement.getDifference())}, columnWidths, margin, yPosition);

                contentStream.close(); // Close the final content stream
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
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
 * @author 2 Zohal Mohammadi
 */