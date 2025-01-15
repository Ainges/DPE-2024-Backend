package service;

import entity.*;
import repository.ApartmentRepository;
import repository.RentalAgreementRepository;
import repository.StatementEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for managing statement entries.
 */
@ApplicationScoped
public class CreateStatementEntryService {

    @Inject
    StatementEntryRepository statementEntryRepository;

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @Inject
    ApartmentRepository apartmentRepository;

    /**
     * Default constructor.
     */
    public CreateStatementEntryService() {
    }


    /**
     * Divides the invoice category sum for the whole year based on the given distribution key.
     *
     * @param currentRentalAgreement the rental agreement for which the sum is being divided
     */
    public List<StatementEntry> divideInvoiceCategorySumWholeYear(RentalAgreement currentRentalAgreement, List<RentalAgreement> allRentalAgreements, HousingObject housingObject, String distributionKey, float invoiceCategorySum, String invoiceCategoryName, String annualStatementPeriod) {

        float amountPerUnit = 0.0f;
        float divisor = 0.0f;
        float amountPayable = 0.0f;
        List<Apartment> apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();
        List<StatementEntry> statementEntries = new ArrayList<>();
        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                /**
                 *    Round for two decimal places
                 */
                amountPayable = Math.round(amountPerUnit * currentRentalAgreement.getApartment().getAreaInM2() * 100.0) / 100.0f;
                statementEntries.add(createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                break;
            case "Tenants":
                for (RentalAgreement ra : allRentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                /**
                 *    Round for two decimal places
                 */
                amountPayable = Math.round(amountPerUnit * currentRentalAgreement.getTenants().size() * 100.0) / 100.0f;
                statementEntries.add(createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                break;
            case "Apartments":
                divisor = apartments.size();
                amountPerUnit = invoiceCategorySum / divisor;
                //Round for two decimal places
                amountPayable = Math.round(amountPerUnit * 100.0) / 100.0f;
                statementEntries.add(createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                break;
            default:
                throw new IllegalArgumentException("Invalid distribution key: " + distributionKey);
        }
        return statementEntries;
    }

    /**
     * Divides the invoice category sum for mid-year tenant changes based on the given distribution key.
     *
     * @param rentalAgreements the list of rental agreements to process
     */
    public List<StatementEntry> divideInvoiceCategorySumMidYear(List<RentalAgreement> rentalAgreements,List<RentalAgreement> allRentalAgreements, HousingObject housingObject, String distributionKey, float invoiceCategorySum, String invoiceCategoryName, String annualStatementPeriod) throws ParseException {

        float amountPerUnit = 0.0f;
        float divisor = 0.0f;
        float amountPayable = 0.0f;

        Date periodStart = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-01-01");
        Date periodEnd = new SimpleDateFormat("yyyy-MM-dd").parse(annualStatementPeriod + "-12-31");

        //List all apartments of the housing object
        List<Apartment> apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();
        //Get the apartment with tenant change for this calculation
        Apartment apartmentWithTenantChange = rentalAgreements.get(0).getApartment();
        List<StatementEntry> statementEntries = new ArrayList<>();

        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }

                //Amount per m²
                amountPerUnit = invoiceCategorySum / divisor;

                /**
                 * Amount for apartment with tenant change per day, round for two decimal places
                 */
                amountPayable = Math.round((amountPerUnit * apartmentWithTenantChange.getAreaInM2()) / 365 * 100.0) / 100.0f;

                for (RentalAgreement ra : rentalAgreements) {

<<<<<<< HEAD
                    /**
                     * Calculate the number of days payable for the rental agreement within the annual statement period
                     */
=======
                    Date rentalStartDate = ra.getStartDate();
                    Date rentalEndDate = ra.getEndDate();

                    if (rentalStartDate.after(periodStart) && rentalStartDate.before(periodEnd)) {
                        periodStart = rentalStartDate;
                    }

                    if ((rentalEndDate != null) && rentalEndDate.after(periodStart) && rentalEndDate.before(periodEnd)) {
                        periodEnd = rentalEndDate;
                    }
                    // Calculate the number of days payable for the rental agreement within the annual statement period
>>>>>>> 066cbe6b9c3ebe56238df67d13f576d007a9e13f
                    float daysPayable = Duration.between(
                            periodStart.toInstant(),
                            periodEnd.toInstant()
                    ).toDays();

                    /**
                     * amountPayable = amount per day * area * number of days
                     */
                    statementEntries.add(createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                }
                break;
            case "Tenants":
                for (RentalAgreement ra : allRentalAgreements) {
                    divisor += ra.getTenants().size();
                }

                /**
                 * Amount per tenant
                 */
                amountPerUnit = invoiceCategorySum / divisor;

                /**
                 * Amount for apartment with tenant change per day, round for two decimal places
                 */
                amountPayable = Math.round(amountPerUnit / 365 * 100.0) / 100.0f;

                for (RentalAgreement ra : rentalAgreements) {

<<<<<<< HEAD
                    /**
                     * Calculate the number of days payable for the rental agreement within the annual statement period
                     */
=======
                    Date rentalStartDate = ra.getStartDate();
                    Date rentalEndDate = ra.getEndDate();

                    if (rentalStartDate.after(periodStart) && rentalStartDate.before(periodEnd)) {
                        periodStart = rentalStartDate;
                    }

                    if ((rentalEndDate != null) && rentalEndDate.after(periodStart) && rentalEndDate.before(periodEnd)) {
                        periodEnd = rentalEndDate;
                    }
                    // Calculate the number of days payable for the rental agreement within the annual statement period
>>>>>>> 066cbe6b9c3ebe56238df67d13f576d007a9e13f
                    float daysPayable = Duration.between(
                            periodStart.toInstant(),
                            periodEnd.toInstant()
                    ).toDays();

                    /**
                     * amountPayable = amount per day * number of tenants * number of days
                     */
                    statementEntries.add(createStatementEntry(amountPayable * ra.getTenants().size() * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                }

                break;
            case "Apartments":
                divisor = apartments.size();

                /**
                 * Amount per apartment
                 */
                amountPerUnit = invoiceCategorySum / divisor;

                /**
                 * Amount for apartment with tenant change per day, round for two decimal places
                 */
                amountPayable = Math.round(amountPerUnit / 365 * 100.0) / 100.0f;

                for (RentalAgreement ra : rentalAgreements) {

<<<<<<< HEAD
                    /**
                     * Calculate the number of days payable for the rental agreement within the annual statement period
                     */
=======
                    Date rentalStartDate = ra.getStartDate();
                    Date rentalEndDate = ra.getEndDate();

                    if (rentalStartDate.after(periodStart) && rentalStartDate.before(periodEnd)) {
                        periodStart = rentalStartDate;
                    }

                    if ((rentalEndDate != null) && rentalEndDate.after(periodStart) && rentalEndDate.before(periodEnd)) {
                        periodEnd = rentalEndDate;
                    }
                    // Calculate the number of days payable for the rental agreement within the annual statement period
>>>>>>> 066cbe6b9c3ebe56238df67d13f576d007a9e13f
                    float daysPayable = Duration.between(
                            periodStart.toInstant(),
                            periodEnd.toInstant()
                    ).toDays();

                    /**
                     * amountPayable = amount per day * number of days
                     */
                    statementEntries.add(createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod));
                }

                break;
            default:
                throw new IllegalArgumentException("Invalid distribution key: " + distributionKey);
        }
        return statementEntries;
    }

    /**
     * Creates a statement entry and persists it in the repository.
     *
     * @param amountPayable     the amount payable for the statement entry
     * @param rentalAgreementId the ID of the rental agreement associated with the statement entry
     */
    @Transactional
    public StatementEntry createStatementEntry(float amountPayable, long rentalAgreementId, String invoiceCategoryName, float invoiceCategorySum, String distributionKey, String annualStatementPeriod) {

        StatementEntry statementEntry = new StatementEntry(invoiceCategoryName, invoiceCategorySum, amountPayable, distributionKey, rentalAgreementRepository.findById(rentalAgreementId), annualStatementPeriod);
        statementEntryRepository.persist(statementEntry);
        return statementEntry;
    }
}

/**
 * End
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */