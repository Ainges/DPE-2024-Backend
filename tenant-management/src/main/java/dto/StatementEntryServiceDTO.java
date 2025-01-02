/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package dto;

import entity.HousingObject;
import entity.RentalAgreement;

import java.util.List;

/**
 * Data Transfer Object for StatementEntryService.
 */
public class StatementEntryServiceDTO {
    private String distributionKey;
    private String invoiceCategoryName;
    private float invoiceCategorySum;
    private HousingObject housingObject;
    private List<RentalAgreement> rentalAgreements;

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
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */