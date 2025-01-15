/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import jakarta.persistence.*;

/**
 * Represents a statement entry entity.
 */
@Entity
public class StatementEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statementEntryId;

    private String name;
    private float overallAmount;
    private float amountPayable;
    private String distributionKey;
    private String annualStatementPeriod; // New attribute

    @ManyToOne
    @JoinColumn(name = "rentalAgreementId")
    private RentalAgreement rentalAgreement;

    @ManyToOne
    @JoinColumn(name = "annualStatementId")
    private AnnualStatement annualStatement;

    /**
     * Default constructor.
     */
    public StatementEntry() {
    }

    /**
     * Constructor without annual statements.
     *
     * @param name                  the name of the statement entry
     * @param overallAmount         the overall amount of the statement entry
     * @param amountPayable         the amount to be paid for the statement entry
     * @param distributionKey       the distribution key of the statement entry
     * @param rentalAgreement       the rental agreement associated with the statement entry
     * @param annualStatementPeriod the annual statement period of the statement entry
     */
    public StatementEntry(String name, float overallAmount, float amountPayable, String distributionKey, RentalAgreement rentalAgreement, String annualStatementPeriod) {
        this.name = name;
        this.overallAmount = overallAmount;
        this.amountPayable = amountPayable;
        this.distributionKey = distributionKey;
        this.rentalAgreement = rentalAgreement;
        this.annualStatementPeriod = annualStatementPeriod;
    }

    /**
     * Parameterized constructor.
     *
     * @param name                  the name of the statement entry
     * @param overallAmount         the overall amount of the statement entry
     * @param amountPayable         the amount to be paid for the statement entry
     * @param distributionKey       the distribution key of the statement entry
     * @param rentalAgreement       the rental agreement associated with the statement entry
     * @param annualStatement       the annual statement associated with the statement entry
     * @param annualStatementPeriod the annual statement period of the statement entry
     */
    public StatementEntry(String name, float overallAmount, float amountPayable, String distributionKey, RentalAgreement rentalAgreement, AnnualStatement annualStatement, String annualStatementPeriod) {
        this.name = name;
        this.overallAmount = overallAmount;
        this.amountPayable = amountPayable;
        this.distributionKey = distributionKey;
        this.rentalAgreement = rentalAgreement;
        this.annualStatement = annualStatement;
        this.annualStatementPeriod = annualStatementPeriod;
    }

    // Getters and Setters

    /**
     * Gets the statement entry ID.
     *
     * @return the statement entry ID
     */
    public long getStatementEntryId() {
        return statementEntryId;
    }

    /**
     * Sets the statement entry ID.
     *
     * @param statementEntryId the statement entry ID
     */
    public void setStatementEntryId(long statementEntryId) {
        this.statementEntryId = statementEntryId;
    }

    /**
     * Gets the name of the statement entry.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the statement entry.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the overall amount of the statement entry.
     *
     * @return the overall amount
     */
    public float getOverallAmount() {
        return overallAmount;
    }

    /**
     * Sets the overall amount of the statement entry.
     *
     * @param overallAmount the overall amount
     */
    public void setOverallAmount(float overallAmount) {
        this.overallAmount = overallAmount;
    }

    /**
     * Gets the amount to be paid for the statement entry.
     *
     * @return the amount to be paid
     */
    public float getAmountPayable() {
        return amountPayable;
    }

    /**
     * Sets the amount to be paid for the statement entry.
     *
     * @param amountPayable the amount to be paid
     */
    public void setAmountPayable(float amountPayable) {
        this.amountPayable = amountPayable;
    }

    /**
     * Gets the distribution key of the statement entry.
     *
     * @return the distribution key
     */
    public String getDistributionKey() {
        return distributionKey;
    }

    /**
     * Sets the distribution key of the statement entry.
     *
     * @param distributionKey the distribution key
     */
    public void setDistributionKey(String distributionKey) {
        this.distributionKey = distributionKey;
    }

    /**
     * Gets the annual statement period of the statement entry.
     *
     * @return the annual statement period
     */
    public String getAnnualStatementPeriod() {
        return annualStatementPeriod;
    }

    /**
     * Sets the annual statement period of the statement entry.
     *
     * @param annualStatementPeriod the annual statement period
     */
    public void setAnnualStatementPeriod(String annualStatementPeriod) {
        this.annualStatementPeriod = annualStatementPeriod;
    }

    /**
     * Gets the rental agreement associated with the statement entry.
     *
     * @return the rental agreement
     */
    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    /**
     * Sets the rental agreement associated with the statement entry.
     *
     * @param rentalAgreement the rental agreement
     */
    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    /**
     * Gets the annual statement associated with the statement entry.
     *
     * @return the annual statement
     */
    public AnnualStatement getAnnualStatement() {
        return annualStatement;
    }

    /**
     * Sets the annual statement associated with the statement entry.
     *
     * @param annualStatement the annual statement
     */
    public void setAnnualStatement(AnnualStatement annualStatement) {
        this.annualStatement = annualStatement;
    }
}
/**
 * End
 *
 * @author 1 Leonie Krauß
 * @author 2 GitHub Copilot
 */