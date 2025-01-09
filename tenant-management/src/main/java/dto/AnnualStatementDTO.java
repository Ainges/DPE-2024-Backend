package dto;

import entity.RentalAgreement;

public class AnnualStatementDTO {
    private RentalAgreement rentalAgreement;
    private String annualStatementPeriod;
    private String periodStart;
    private String periodEnd;

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
    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }
}