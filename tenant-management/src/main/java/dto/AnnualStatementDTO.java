/**
 * @author 1 Moritz Baur
 * @author 2 GitHub Copilot
 */
package dto;

/**
 * define the import statement for the RentalAgreement class and the beginning of the AnnualStatementDTO class, including its fields
 * imports the RentalAgreement class from the entity packag
 */

import entity.RentalAgreement;

/**
 * The code defines a class AnnualStatementDTO with two private fields representing annual statement details
 * The class is used to store and transfer data related to annual statements
 */
public class AnnualStatementDTO {
    private RentalAgreement rentalAgreement;
    private String annualStatementPeriod;

    /**
     * Code defines getter and setter methods for the rentalAgreement and annualStatementPeriod fields in the AnnualStatementDTO class.
     * These methods allow for accessing and modifying the values of these private fields from outside the class
     * Getters and Setters
     * getRentalAgreement(): Returns the value of the rentalAgreement field.
     * setRentalAgreement(RentalAgreement rentalAgreement): Sets the value of the rentalAgreement field.
     * getAnnualStatementPeriod(): Returns the value of the annualStatementPeriod field.
     * setAnnualStatementPeriod(String annualStatementPeriod): Sets the value of the annualStatementPeriod field.
     */
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