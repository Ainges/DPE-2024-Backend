package entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents an invoice entity.
 */
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    private Date invoiceDate;
    private float invoiceAmount;
    private String description;

    @ManyToOne
    @JoinColumn(name = "invoiceCategoryId")
    private InvoiceCategory invoiceCategory;

    @ManyToOne
    @JoinColumn(name = "housingObjectId")
    private HousingObject housingObject;

    /**
     * Default constructor.
     */
    public Invoice() {
    }

    /**
     * Parameterized constructor.
     *
     * @param invoiceDate     the date of the invoice
     * @param invoiceAmount   the amount of the invoice
     * @param description     the description of the invoice
     * @param invoiceCategory the category of the invoice
     * @param housingObject   the housing object associated with the invoice
     */
    public Invoice(Date invoiceDate, float invoiceAmount, String description, InvoiceCategory invoiceCategory, HousingObject housingObject) {
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.description = description;
        this.invoiceCategory = invoiceCategory;
        this.housingObject = housingObject;
    }

    // Getters and Setters

    /**
     * Gets the ID of the invoice.
     *
     * @return the ID of the invoice
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the ID of the invoice.
     *
     * @param invoiceId the ID of the invoice
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets the date of the invoice.
     *
     * @return the date of the invoice
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the date of the invoice.
     *
     * @param invoiceDate the date of the invoice
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Gets the amount of the invoice.
     *
     * @return the amount of the invoice
     */
    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Sets the amount of the invoice.
     *
     * @param invoiceAmount the amount of the invoice
     */
    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * Gets the description of the invoice.
     *
     * @return the description of the invoice
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the invoice.
     *
     * @param description the description of the invoice
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the invoice.
     *
     * @return the category of the invoice
     */
    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    /**
     * Sets the category of the invoice.
     *
     * @param invoiceCategory the category of the invoice
     */
    public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    /**
     * Gets the housing object associated with the invoice.
     *
     * @return the housing object associated with the invoice
     */
    public HousingObject getHousingObject() {
        return housingObject;
    }

    /**
     * Sets the housing object associated with the invoice.
     *
     * @param housingObject the housing object associated with the invoice
     */
    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }
}