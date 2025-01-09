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

        // 1. Create Annual Statement
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);
        annualStatement.setPeriodStart(new SimpleDateFormat("dd.MM.yyyy").parse("01.01." + annualStatementPeriod));
        annualStatement.setPeriodEnd(new SimpleDateFormat("dd.MM.yyyy").parse("31.12." + annualStatementPeriod));

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
        AnnualStatement annualStatement = new AnnualStatement();
        annualStatement.setRentalAgreement(rentalAgreement);
        annualStatement.setPeriodStart(new SimpleDateFormat("dd.MM.yyyy").parse(periodStart));
        annualStatement.setPeriodEnd(new SimpleDateFormat("dd.MM.yyyy").parse(periodEnd));

        // Save the Annual Statement initially
        annualStatementRepository.persist(annualStatement);
        List<StatementEntry> statementEntries = statementEntryRepository
                .find("rentalAgreement.rentalAgreementId = ?1 and periodStart >= ?2 and periodEnd <= ?3",
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
        long months = (annualStatement.getPeriodEnd().getTime() - annualStatement.getPeriodStart().getTime()) / (1000 * 60 * 60 * 24 * 30);
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
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Annual Statement");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.showText("Rental Agreement ID: " + rentalAgreement.getRentalAgreementId());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period Start: " + annualStatement.getPeriodStart());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Period End: " + annualStatement.getPeriodEnd());
                contentStream.newLineAtOffset(0, -15);

                float totalCost = 0.0f;
                for (StatementEntry statementEntry : statementEntries) {
                    contentStream.showText("Statement Entry: " + statementEntry.getName());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Amount Payable: " + statementEntry.getAmountPayable());
                    contentStream.newLineAtOffset(0, -15);
                    totalCost += statementEntry.getAmountPayable();
                }

                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Total Cost: " + totalCost);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Total Prepayments: " + annualStatement.getTotalPrepayments());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Difference: " + annualStatement.getDifference());

                contentStream.endText();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }
}
/**
 * End
 *
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */