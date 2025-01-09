package dto;

import entity.RentalAgreement;

public class AnnualStatementDTO {
    private RentalAgreement rentalAgreement;
    private String annualStatementPeriod;


    // Getters and Setters
    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public String getAnnualStatementPeriod() {
        return annualStatementPeriod;
    }

    public void setAnnualStatementPeriod(String annualStatementPeriod) {
        this.annualStatementPeriod = annualStatementPeriod;
    }
}