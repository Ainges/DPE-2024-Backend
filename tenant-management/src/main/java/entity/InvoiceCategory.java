/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class InvoiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceCategoryId;

    private String name;
    private String unit;
    private String distributionKey;

    // Annual Statement is the owner of the m:n relationship with Invoice Category.
    // Therefore, the relationships should be created, when the Annual Statement is created.
    @ManyToMany(mappedBy = "invoiceCategories")
    @JsonIgnore
    private Set<AnnualStatement> annualStatements;

    // Default constructor
    public InvoiceCategory() {
    }

    // Constructor without annual statements
    public InvoiceCategory(String name, String unit, String distributionKey) {
        this.name = name;
        this.unit = unit;
        this.distributionKey = distributionKey;
    }

    // Constructor with annual statements
    public InvoiceCategory(String name, String unit, String distributionKey, Set<AnnualStatement> annualStatements) {
        this.name = name;
        this.unit = unit;
        this.distributionKey = distributionKey;
        this.annualStatements = annualStatements;
    }

    // Getters and Setters
    public long getInvoiceCategoryId() {
        return invoiceCategoryId;
    }

    public void setInvoiceCategoryId(long invoiceCategoryId) {
        this.invoiceCategoryId = invoiceCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDistributionKey() {
        return distributionKey;
    }

    public void setDistributionKey(String distributionKey) {
        this.distributionKey = distributionKey;
    }

    public Set<AnnualStatement> getAnnualStatements() {
        return annualStatements;
    }

    public void setAnnualStatements(Set<AnnualStatement> annualStatements) {
        this.annualStatements = annualStatements;
    }
}
/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */