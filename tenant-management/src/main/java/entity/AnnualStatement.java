/** Moritz Baur: Created using GitHub Copilot */

package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;

@Entity
public class AnnualStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int annualStatementId;

    @ManyToOne
    @JoinColumn(name = "rentalAgreementId")
    private RentalAgreement rentalAgreement;

    private Date periodStart;
    private Date periodEnd;
    private float totalCost;
    private float totalPrepayments;
    private float difference;

    // Getters and Setters
    public int getAnnualStatementId() {
        return annualStatementId;
    }

    public void setAnnualStatementId(int annualStatementId) {
        this.annualStatementId = annualStatementId;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getTotalPrepayments() {
        return totalPrepayments;
    }

    public void setTotalPrepayments(float totalPrepayments) {
        this.totalPrepayments = totalPrepayments;
    }

    public float getDifference() {
        return difference;
    }

    public void setDifference(float difference) {
        this.difference = difference;
    }
}