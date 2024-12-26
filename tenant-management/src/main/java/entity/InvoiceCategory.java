/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InvoiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceCategoryId;

    private String name;
    private String unit;
    private String distributionKey;

    // Getters and Setters
    public int getInvoiceCategoryId() {
        return invoiceCategoryId;
    }

    public void setInvoiceCategoryId(int invoiceCategoryId) {
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
}
/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */