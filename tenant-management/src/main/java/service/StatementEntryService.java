/**
 * Start
 *
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */

/**
 * Service for managing statement entries.
 * This service provides methods to handle statement entries, including dividing invoice category sums
 * based on different distribution keys and creating statement entries.
 */
package service;

import entity.*;

import repository.ApartmentRepository;
import repository.RentalAgreementRepository;
import repository.StatementEntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service for managing statement entries.
 */
@ApplicationScoped
public class StatementEntryService {

    @Inject
    StatementEntryRepository statementEntryRepository;

    @Inject
    RentalAgreementRepository rentalAgreementRepository;

    @Inject
    ApartmentRepository apartmentRepository;

    private String distributionKey;
    private String invoiceCategoryName;
    private float invoiceCategorySum;
    private HousingObject housingObject;
    private List<RentalAgreement> rentalAgreements;
    private List<Apartment> apartments;

    /**
     * Default constructor.
     */
    public StatementEntryService() {
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
    public StatementEntryService(String distributionKey, String invoiceCategoryName, float invoiceCategorySum, HousingObject housingObject, List<RentalAgreement> rentalAgreements) {
        this.distributionKey = distributionKey;
        this.invoiceCategoryName = invoiceCategoryName;
        this.invoiceCategorySum = invoiceCategorySum;
        this.housingObject = housingObject;
        this.rentalAgreements = rentalAgreements;
        this.apartments = apartmentRepository.find("housingObject.housingObjectId", housingObject.getHousingObjectId()).list();
    }

    /**
     * Divides the invoice category sum for the whole year based on the given distribution key.
     *
     * @param rentalAgreement    the rental agreement for which the sum is being divided
     * @return the amount per unit based on the distribution key
     */
    public void divideInvoiceCategorySumWholeYear(RentalAgreement rentalAgreement) {
        float amountPerUnit = 0.0f;
        float divisor = 0.0f;

        switch (distributionKey) {
            case "Area":
                for (Apartment apartment : apartments) {
                    divisor += apartment.getAreaInM2();
                }

                // Calculate Amount per M2
                amountPerUnit = invoiceCategorySum / divisor;

                createStatementEntry(amountPerUnit * rentalAgreement.getApartment().getAreaInM2(), rentalAgreement.getRentalAgreementId());

                break;
            case "Tenants":
                for (RentalAgreement ra : rentalAgreements) {
                    divisor += ra.getTenants().size();
                }
                // Calculate Amount per Tenant
                amountPerUnit = invoiceCategorySum / divisor;

                // Save divided StatementEntries
                createStatementEntry(amountPerUnit * rentalAgreement.getTenants().size(), rentalAgreement.getRentalAgreementId());

                break;
            case "Apartments":
                divisor = apartments.size();

                // Calculate Amount per Apartment
                amountPerUnit = invoiceCategorySum / divisor;

                // Save divided StatementEntries
                createStatementEntry(amountPerUnit, rentalAgreement.getRentalAgreementId());
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
     * Gets the distribution key.
     *
     * @return the distribution key
     */
    public String getDistributionKey() {
        return distributionKey;
    }

    /**
     * Sets the distribution key.
     *
     * @param distributionKey the distribution key
     */
    public void setDistributionKey(String distributionKey) {
        this.distributionKey = distributionKey;
    }

    /**
     * Gets the invoice category name.
     *
     * @return the invoice category name
     */
    public String getInvoiceCategoryName() {
        return invoiceCategoryName;
    }

    /**
     * Sets the invoice category name.
     *
     * @param invoiceCategoryName the invoice category name
     */
    public void setInvoiceCategoryName(String invoiceCategoryName) {
        this.invoiceCategoryName = invoiceCategoryName;
    }

    /**
     * Gets the invoice category sum.
     *
     * @return the invoice category sum
     */
    public float getInvoiceCategorySum() {
        return invoiceCategorySum;
    }

    /**
     * Sets the invoice category sum.
     *
     * @param invoiceCategorySum the invoice category sum
     */
    public void setInvoiceCategorySum(float invoiceCategorySum) {
        this.invoiceCategorySum = invoiceCategorySum;
    }

    /**
     * Gets the housing object.
     *
     * @return the housing object
     */
    public HousingObject getHousingObject() {
        return housingObject;
    }

    /**
     * Sets the housing object.
     *
     * @param housingObject the housing object
     */
    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }

    /**
     * Gets the list of rental agreements.
     *
     * @return the list of rental agreements
     */
    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    /**
     * Sets the list of rental agreements.
     *
     * @param rentalAgreements the list of rental agreements
     */
    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    /**
     * Gets the list of apartments.
     *
     * @return the list of apartments
     */
    public List<Apartment> getApartments() {
        return apartments;
    }

    /**
     * Sets the list of apartments.
     *
     * @param apartments the list of apartments
     */
    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }
}