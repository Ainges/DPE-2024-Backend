package dto;

import entity.HousingObject;
import entity.RentalAgreement;

import java.util.List;

public class StatementEntryServiceDTO {
    private String distributionKey;
    private String invoiceCategoryName;
    private float invoiceCategorySum;
    private HousingObject housingObject;
    private List<RentalAgreement> rentalAgreements;

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