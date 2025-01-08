package service;

import entity.AnnualStatement;
import entity.RentalAgreement;
import entity.StatementEntry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
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
    public Response createAnnualStatement(AnnualStatement annualStatement) {
        // Check if the ID already exists
        if (annualStatementRepository.findById(annualStatement.getAnnualStatementId()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("An AnnualStatement with ID " + annualStatement.getAnnualStatementId() + " already exists.")
                    .build();
        }
        // Save the AnnualStatement
        annualStatementRepository.persist(annualStatement);
        // Return success response
        return Response.status(Response.Status.CREATED)
                .entity(annualStatement)
                .build();
    }

    /**
     * Generates annual statements for all rental agreements.
     */
    public void generateAnnualStatements() {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.findAll().list();
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            // 1. Create Annual Statement
            AnnualStatement annualStatement = new AnnualStatement();
            annualStatement.setRentalAgreement(rentalAgreement);
            annualStatement.setPeriodStart(rentalAgreement.getStartDate());
            annualStatement.setPeriodEnd(rentalAgreement.getEndDate());

            List<StatementEntry> statementEntries = statementEntryRepository
                    .find("rentalAgreement.rentalAgreementId = ?1",
                            rentalAgreement.getRentalAgreementId())
                    .list();

            String annualStatementPeriod = "";
            for (StatementEntry statementEntry : statementEntries) {
                annualStatementPeriod = statementEntry.getAnnualStatementPeriod();
            }

            statementEntries = statementEntryRepository
                    .find("rentalAgreement.rentalAgreementId = ?1 and annualStatementPeriod = ?2",
                            rentalAgreement.getRentalAgreementId(),
                            annualStatementPeriod)
                    .list();

            // Calculate prepayments
            float heatingCostPrepayment = rentalAgreement.getApartment().getHeatingCostPrepayment();
            float additionalCostPrepayment = rentalAgreement.getApartment().getAdditionalCostPrepayment();
            float totalPrepayments = heatingCostPrepayment + additionalCostPrepayment;

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

            // Save the Annual Statement
            annualStatementRepository.persist(annualStatement);
        }
    }

    // erstelle mir jetzt einen PDFCreate Methode, über die AnnualStatementid der alle Statemententries auflistet, welches zu der gleichen Rentelagreement gehören. Dann am ENde die Total Kost auflistet, sowie die Periode und die totelPerepayment period start sowie period ende
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
 * @author 1 GitHub Copilot
 * @author 2 Zohal Mohammadi
 */