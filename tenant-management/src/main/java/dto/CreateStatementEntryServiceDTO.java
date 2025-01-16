/**
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */
package dto;

import entity.HousingObject;
import entity.RentalAgreement;

import java.util.List;

/**
 * Data Transfer Object for CreateStatementEntryService.
 * defines the CreateStatementEntryServiceDTO class and its member variables
 * This class is a Data Transfer Object (DTO) used to transfer data related to creating a statement entry
 */
public class CreateStatementEntryServiceDTO {
    private String distributionKey;
    private String invoiceCategoryName;
    private float invoiceCategorySum;
    private HousingObject housingObject;
    private List<RentalAgreement> rentalAgreements;
    private String annualStatementPeriod;

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

    public String getAnnualStatementPeriod() {
        return annualStatementPeriod;
    }

    public void setAnnualStatementPeriod(String annualStatementPeriod) {
        this.annualStatementPeriod = annualStatementPeriod;
    }
}