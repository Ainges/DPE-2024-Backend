/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

/**
 * Represents an annual statement for a rental agreement.
 * @Entity: Marks the class as a JPA entity.
 * @Id: Specifies the primary key of the entity.
 * @GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value is automatically generated.
 * private long annualStatementId: The primary key field.
 * @ManyToOne: Specifies a many-to-one relationship with another entity.
 * @JoinColumn(name = "rentalAgreementId"): Specifies the foreign key column.
 *
 */


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

    /**
     * Default constructor.
     */
    public AnnualStatement() {
    }

    /**
     * Constructor without invoice categories.
     *
     * @param rentalAgreement  the rental agreement associated with this annual statement
     * @param periodStart      the start date of the period
     * @param periodEnd        the end date of the period
     * @param totalCost        the total cost for the period
     * @param totalPrepayments the total prepayments for the period
     * @param difference       the difference between total cost and total prepayments
     */
    public AnnualStatement(RentalAgreement rentalAgreement, Date periodStart, Date periodEnd, float totalCost, float totalPrepayments, float difference) {
        this.rentalAgreement = rentalAgreement;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.totalCost = totalCost;
        this.totalPrepayments = totalPrepayments;
        this.difference = difference;
    }

    // Getters and Setters

    /**
     * Gets the ID of the annual statement.
     *
     * @return the ID of the annual statement
     */
    public long getAnnualStatementId() {
        return annualStatementId;
    }

    /**
     * Sets the ID of the annual statement.
     *
     * @param annualStatementId the ID of the annual statement
     */
    public void setAnnualStatementId(long annualStatementId) {
        this.annualStatementId = annualStatementId;
    }

    /**
     * Gets the rental agreement associated with this annual statement.
     *
     * @return the rental agreement
     */
    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    /**
     * Sets the rental agreement associated with this annual statement.
     *
     * @param rentalAgreement the rental agreement
     */
    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    /**
     * Gets the start date of the period.
     *
     * @return the start date of the period
     */
    public Date getPeriodStart() {
        return periodStart;
    }

    /**
     * Sets the start date of the period.
     *
     * @param periodStart the start date of the period
     */
    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    /**
     * Gets the end date of the period.
     *
     * @return the end date of the period
     */
    public Date getPeriodEnd() {
        return periodEnd;
    }

    /**
     * Sets the end date of the period.
     *
     * @param periodEnd the end date of the period
     */
    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    /**
     * Gets the total cost for the period.
     *
     * @return the total cost for the period
     */
    public float getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost for the period.
     *
     * @param totalCost the total cost for the period
     */
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Gets the total prepayments for the period.
     *
     * @return the total prepayments for the period
     */
    public float getTotalPrepayments() {
        return totalPrepayments;
    }

    /**
     * Sets the total prepayments for the period.
     *
     * @param totalPrepayments the total prepayments for the period
     */
    public void setTotalPrepayments(float totalPrepayments) {
        this.totalPrepayments = totalPrepayments;
    }

    /**
     * Gets the difference between total cost and total prepayments.
     *
     * @return the difference between total cost and total prepayments
     */
    public float getDifference() {
        return difference;
    }

    /**
     * Sets the difference between total cost and total prepayments.
     *
     * @param difference the difference between total cost and total prepayments
     */
    public void setDifference(float difference) {
        this.difference = difference;
    }

}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */