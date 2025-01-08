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

    @Inject
    InvoiceCategorySumService invoiceCategorySumService;

    private String distributionKey;
    private String invoiceCategoryName;
    private float invoiceCategorySum;
    private HousingObject housingObject;
    private List<RentalAgreement> rentalAgreements;

    /**
     * Default constructor.
     */
    public CreateStatementEntryService() {
    }

    /**
     * Constructor with parameters.
     *
     * @param distributionKey     the distribution key used for dividing the invoice category sum
     * @param invoiceCategoryName the name of the invoice category
     * @param invoiceCategorySum  the total sum of the invoice category
     * @param housingObject       the housing object associated with the statement entries
     * @param rentalAgreements    the list of rental agreements associated with the housing object
     */
    public CreateStatementEntryService(String distributionKey, String invoiceCategoryName, float invoiceCategorySum, HousingObject housingObject, List<RentalAgreement> rentalAgreements) {
        this.distributionKey = distributionKey;
        this.invoiceCategoryName = invoiceCategoryName;
        this.invoiceCategorySum = invoiceCategorySum;
        this.housingObject = housingObject;
        this.rentalAgreements = rentalAgreements;
    }

    /**
     * Divides the invoice category sum for the whole year based on the given distribution key.
     *
     * @param rentalAgreement the rental agreement for which the sum is being divided
     */
    public void divideInvoiceCategorySumWholeYear(RentalAgreement rentalAgreement) {
        calculateInvoiceCategorySum();
        float amountPerUnit = 0.0f;
        float divisor = 0.0f;
        List<Apartment> apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();
        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }
                amountPerUnit = invoiceCategorySum / divisor;
                createStatementEntry(amountPerUnit * rentalAgreement.getApartment().getAreaInM2(), rentalAgreement.getRentalAgreementId());
                break;
            case "Tenants":
                for (RentalAgreement ra : rentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                amountPerUnit = invoiceCategorySum / divisor;
                createStatementEntry(amountPerUnit * rentalAgreement.getTenants().size(), rentalAgreement.getRentalAgreementId());
                break;
            case "Apartments":
                divisor = apartments.size();
                amountPerUnit = invoiceCategorySum / divisor;
                createStatementEntry(amountPerUnit, rentalAgreement.getRentalAgreementId());
                break;
            default:
                throw new IllegalArgumentException("Invalid distribution key: " + distributionKey);
        }
    }

    /**
     * Only for Mid-Year Tenant Changes! - to be implemented
     /**
     * Divides the invoice category sum for mid-year tenant changes based on the given distribution key.
     *
     * @param rentalAgreements the list of rental agreements to process
     */
    public void divideInvoiceCategorySumMidYear(List<RentalAgreement> rentalAgreements) {
        calculateInvoiceCategorySum();
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
                    float amountPayable = (amountPerUnit * ra.getApartment().getAreaInM2()) / 365;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId());
                }
                break;
            case "Tenants":
                for (RentalAgreement ra : rentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                amountPerUnit = invoiceCategorySum / divisor;

                for (RentalAgreement ra : rentalAgreements) {

                    float daysPayable = Duration.between(ra.getStartDate().toInstant(), ra.getEndDate().toInstant()).toDays();
                    float amountPayable = (amountPerUnit * ra.getTenants().size()) / 365;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId());
                }

                break;
            case "Apartments":
                divisor = apartments.size();
                amountPerUnit = invoiceCategorySum / divisor;

                for (RentalAgreement ra : rentalAgreements) {

                    float daysPayable = Duration.between(ra.getStartDate().toInstant(), ra.getEndDate().toInstant()).toDays();
                    float amountPayable = amountPerUnit / 365;
                    createStatementEntry(amountPayable * daysPayable, ra.getRentalAgreementId());
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
    public void createStatementEntry(float amountPayable, long rentalAgreementId) {
        statementEntryRepository.persist(new StatementEntry(this.invoiceCategoryName, this.invoiceCategorySum, amountPayable, this.distributionKey, rentalAgreementRepository.findById(rentalAgreementId)));
    }

    /**
     * Calculates the invoice category sum using the InvoiceCategorySumService.
     */
    public void calculateInvoiceCategorySum() {
        this.invoiceCategorySum = (float) invoiceCategorySumService.getCategoryTotalSumById(this.housingObject.getHousingObjectId());
    }
    // Getters and Setters

    public String getDistributionKey() {
        return distributionKey;
    }

    public void setDistributionKey(String distributionKey) {
        this.distributionKey = distributionKey;
    }

    public String getInvoiceCategoryName() {
        return invoiceCategoryName;
    }

    public void setInvoiceCategoryName(String invoiceCategoryName) {
        this.invoiceCategoryName = invoiceCategoryName;
    }

    public float getInvoiceCategorySum() {
        return invoiceCategorySum;
    }

    public void setInvoiceCategorySum(float invoiceCategorySum) {
        this.invoiceCategorySum = invoiceCategorySum;
    }

    public HousingObject getHousingObject() {
        return housingObject;
    }

    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }
}

/**
 * End
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */