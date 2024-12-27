/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Represents an invoice category entity.
 */
@Entity
public class InvoiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceCategoryId;

    private String name;
    private String unit;
    private String distributionKey;

    /**
     * Annual Statement is the owner of the m:n relationship with Invoice Category.
     * Therefore, the relationships should be created, when the Annual Statement is created.
     */
    @ManyToMany(mappedBy = "invoiceCategories")
    @JsonIgnore
    private Set<AnnualStatement> annualStatements;

    /**
     * Default constructor.
     */
    public InvoiceCategory() {
    }

    /**
     * Constructor without annual statements.
     *
     * @param name            the name of the invoice category
     * @param unit            the unit of the invoice category
     * @param distributionKey the distribution key of the invoice category
     */
    public InvoiceCategory(String name, String unit, String distributionKey) {
        this.name = name;
        this.unit = unit;
        this.distributionKey = distributionKey;
    }

    /**
     * Constructor with annual statements.
     *
     * @param name             the name of the invoice category
     * @param unit             the unit of the invoice category
     * @param distributionKey  the distribution key of the invoice category
     * @param annualStatements the set of annual statements associated with this invoice category
     */
    public InvoiceCategory(String name, String unit, String distributionKey, Set<AnnualStatement> annualStatements) {
        this.name = name;
        this.unit = unit;
        this.distributionKey = distributionKey;
        this.annualStatements = annualStatements;
    }

    // Getters and Setters

    /**
     * Gets the ID of the invoice category.
     *
     * @return the ID of the invoice category
     */
    public long getInvoiceCategoryId() {
        return invoiceCategoryId;
    }

    /**
     * Sets the ID of the invoice category.
     *
     * @param invoiceCategoryId the ID of the invoice category
     */
    public void setInvoiceCategoryId(long invoiceCategoryId) {
        this.invoiceCategoryId = invoiceCategoryId;
    }

    /**
     * Gets the name of the invoice category.
     *
     * @return the name of the invoice category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the invoice category.
     *
     * @param name the name of the invoice category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the unit of the invoice category.
     *
     * @return the unit of the invoice category
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of the invoice category.
     *
     * @param unit the unit of the invoice category
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the distribution key of the invoice category.
     *
     * @return the distribution key of the invoice category
     */
    public String getDistributionKey() {
        return distributionKey;
    }

    /**
     * Sets the distribution key of the invoice category.
     *
     * @param distributionKey the distribution key of the invoice category
     */
    public void setDistributionKey(String distributionKey) {
        this.distributionKey = distributionKey;
    }

    /**
     * Gets the set of annual statements associated with this invoice category.
     *
     * @return the set of annual statements
     */
    public Set<AnnualStatement> getAnnualStatements() {
        return annualStatements;
    }

    /**
     * Sets the set of annual statements associated with this invoice category.
     *
     * @param annualStatements the set of annual statements
     */
    public void setAnnualStatements(Set<AnnualStatement> annualStatements) {
        this.annualStatements = annualStatements;
    }
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */