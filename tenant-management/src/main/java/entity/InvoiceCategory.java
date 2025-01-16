/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */
/** * @author 1 Leonie Krauß, Moritz Baur * @author 2 GitHub Copilot */
package entity;

import jakarta.persistence.*;

/**
 * Represents an invoice category entity.
 */
@Entity
public class InvoiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceCategoryId;

    private String name;

    /**
     * Default constructor.
     */
    public InvoiceCategory() {
    }

    /**
     * Constructor without annual statements.
     *
     * @param name the name of the invoice category
     */
    public InvoiceCategory(String name) {
        this.name = name;
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
}
