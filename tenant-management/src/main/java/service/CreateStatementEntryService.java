package service;

import entity.*;
import repository.ApartmentRepository;
import repository.RentalAgreementRepository;
import repository.StatementEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.*;

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
    public void divideInvoiceCategorySumWholeYear(RentalAgreement currentRentalAgreement, List<RentalAgreement> allRentalAgreements, HousingObject housingObject, String distributionKey, float invoiceCategorySum, String invoiceCategoryName, String annualStatementPeriod) {

        float amountPerUnit = 0.0f;
        float divisor = 0.0f;
        float amountPayable = 0.0f;
        List<Apartment> apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();
        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                //Round for two decimal places
                amountPayable = Math.round(amountPerUnit * currentRentalAgreement.getApartment().getAreaInM2() * 100.0) / 100.0f;
                createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                break;
            case "Tenants":
                for (RentalAgreement ra : allRentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                //Round for two decimal places
                amountPayable = Math.round(amountPerUnit * currentRentalAgreement.getTenants().size() * 100.0) / 100.0f;
                createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                break;
            case "Apartments":
                divisor = apartments.size();
                amountPerUnit = invoiceCategorySum / divisor;
                //Round for two decimal places
                amountPayable = Math.round(amountPerUnit * 100.0) / 100.0f;
                createStatementEntry(amountPayable, currentRentalAgreement.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                break;
            default:
                throw new IllegalArgumentException("Invalid distribution key: " + distributionKey);
        }
    }

    /**
     * Only for Mid-Year Tenant Changes! - to be implemented
     * /**
     * Divides the invoice category sum for mid-year tenant changes based on the given distribution key.
     *
     * @param rentalAgreements the list of rental agreements to process
     */
    public void divideInvoiceCategorySumMidYear(List<RentalAgreement> rentalAgreements, HousingObject housingObject, String distributionKey, float invoiceCategorySum, String invoiceCategoryName, String annualStatementPeriod) {

        float amountPerUnit = 0.0f;
        float divisor = 0.0f;
        List<Apartment> apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();

        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                for (RentalAgreement ra : rentalAgreements) {

                    float daysPayable = Duration.between(ra.getStartDate().toInstant(), ra.getEndDate().toInstant()).toDays();
                    //Round for two decimal places
                    float amountPayable = Math.round((amountPerUnit * ra.getApartment().getAreaInM2()) / 365 * 100.0) / 100.0f;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                }
                break;
            case "Tenants":
                for (RentalAgreement ra : rentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                for (RentalAgreement ra : rentalAgreements) {

                    float daysPayable = Duration.between(ra.getStartDate().toInstant(), ra.getEndDate().toInstant()).toDays();
                    //Round for two decimal places
                    float amountPayable = Math.round((amountPerUnit * ra.getTenants().size()) / 365 * 100.0) / 100.0f;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                }

                break;
            case "Apartments":
                divisor = apartments.size();
                amountPerUnit = invoiceCategorySum / divisor;

                for (RentalAgreement ra : rentalAgreements) {

                    float daysPayable = Duration.between(ra.getStartDate().toInstant(), ra.getEndDate().toInstant()).toDays();
                    //Round for two decimal places
                    float amountPayable = Math.round(amountPerUnit / 365 * 100.0) / 100.0f;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId(), invoiceCategoryName, invoiceCategorySum, distributionKey, annualStatementPeriod);
                }

                break;
            default:
                throw new IllegalArgumentException("Invalid distribution key: " + distributionKey);
        }
    }

    /**
     * Creates a statement entry and persists it in the repository.
     *
     * @param amountPayable     the amount payable for the statement entry
     * @param rentalAgreementId the ID of the rental agreement associated with the statement entry
     */
    @Transactional
    public void createStatementEntry(float amountPayable, long rentalAgreementId, String invoiceCategoryName, float invoiceCategorySum, String distributionKey, String annualStatementPeriod) {
        statementEntryRepository.persist(new StatementEntry(invoiceCategoryName, invoiceCategorySum, amountPayable, distributionKey, rentalAgreementRepository.findById(rentalAgreementId), annualStatementPeriod));
    }
}

/**
 * End
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */