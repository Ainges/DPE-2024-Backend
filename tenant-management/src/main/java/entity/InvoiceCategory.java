/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package entity;

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

    @ManyToMany
    @JoinTable(
            name = "InvoiceCategory_AnnualStatement",
            joinColumns = @JoinColumn(name = "invoiceCategoryId"),
            inverseJoinColumns = @JoinColumn(name = "annualStatementId")
    )
    private Set<AnnualStatement> annualStatements;

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