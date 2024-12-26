/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class AnnualStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long annualStatementId;

    @ManyToOne
    @JoinColumn(name = "rentalAgreementId")
    private RentalAgreement rentalAgreement;

    private Date periodStart;
    private Date periodEnd;
    private float totalCost;
    private float totalPrepayments;
    private float difference;

    @ManyToMany(mappedBy = "annualStatements")
    private Set<InvoiceCategory> invoiceCategories;

    // Getters and Setters
    public long getAnnualStatementId() {
        return annualStatementId;
    }

    public void setAnnualStatementId(long annualStatementId) {
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

    public Set<InvoiceCategory> getInvoiceCategories() {
        return invoiceCategories;
    }

    public void setInvoiceCategories(Set<InvoiceCategory> invoiceCategories) {
        this.invoiceCategories = invoiceCategories;
    }
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */