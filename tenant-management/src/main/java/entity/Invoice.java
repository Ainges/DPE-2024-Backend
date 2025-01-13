/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */
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
    private String status;
    private String receiver;
    private String receiverIban;
    private String receiverBic;
    private String externalInvoiceNumber;
    private String currency;
    private boolean relevantForAnnualStatement;

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
     * @param invoiceDate                the date of the invoice
     * @param invoiceAmount              the amount of the invoice
     * @param description                the description of the invoice
     * @param status                     the status of the invoice
     * @param receiver                   the receiver of the invoice
     * @param receiverIban               the IBAN of the receiver
     * @param receiverBic                the BIC of the receiver
     * @param externalInvoiceNumber      the external invoice number
     * @param currency                   the currency of the invoice in ISO 4217 format
     * @param relevantForAnnualStatement indicates if the invoice is relevant for the annual statement
     * @param invoiceCategory            the category of the invoice
     * @param housingObject              the housing object associated with the invoice
     */
    public Invoice(Date invoiceDate, float invoiceAmount, String description, String status, String receiver, String receiverIban, String receiverBic, String externalInvoiceNumber, String currency, boolean relevantForAnnualStatement, InvoiceCategory invoiceCategory, HousingObject housingObject) {
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.description = description;
        this.status = status;
        this.receiver = receiver;
        this.receiverIban = receiverIban;
        this.receiverBic = receiverBic;
        this.externalInvoiceNumber = externalInvoiceNumber;
        this.currency = currency;
        this.relevantForAnnualStatement = relevantForAnnualStatement;
        this.invoiceCategory = invoiceCategory;
        this.housingObject = housingObject;
    }

    // Getters and Setters

    /**
     * Gets the invoice ID.
     *
     * @return the invoice ID
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the invoice ID.
     *
     * @param invoiceId the invoice ID
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets the invoice date.
     *
     * @return the invoice date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Sets the invoice date.
     *
     * @param invoiceDate the invoice date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Gets the invoice amount.
     *
     * @return the invoice amount
     */
    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Sets the invoice amount.
     *
     * @param invoiceAmount the invoice amount
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
     * Gets the status of the invoice.
     *
     * @return the status of the invoice
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the invoice.
     *
     * @param status the status of the invoice
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the receiver of the invoice.
     *
     * @return the receiver of the invoice
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the invoice.
     *
     * @param receiver the receiver of the invoice
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the IBAN of the receiver.
     *
     * @return the IBAN of the receiver
     */
    public String getReceiverIban() {
        return receiverIban;
    }

    /**
     * Sets the IBAN of the receiver.
     *
     * @param receiverIban the IBAN of the receiver
     */
    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    /**
     * Gets the BIC of the receiver.
     *
     * @return the BIC of the receiver
     */
    public String getReceiverBic() {
        return receiverBic;
    }

    /**
     * Sets the BIC of the receiver.
     *
     * @param receiverBic the BIC of the receiver
     */
    public void setReceiverBic(String receiverBic) {
        this.receiverBic = receiverBic;
    }

    /**
     * Gets the external invoice number.
     *
     * @return the external invoice number
     */
    public String getExternalInvoiceNumber() {
        return externalInvoiceNumber;
    }

    /**
     * Sets the external invoice number.
     *
     * @param externalInvoiceNumber the external invoice number
     */
    public void setExternalInvoiceNumber(String externalInvoiceNumber) {
        this.externalInvoiceNumber = externalInvoiceNumber;
    }

    /**
     * Gets the currency of the invoice.
     *
     * @return the currency of the invoice
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of the invoice.
     *
     * @param currency the currency of the invoice
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the invoice category.
     *
     * @return the invoice category
     */
    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    /**
     * Sets the invoice category.
     *
     * @param invoiceCategory the invoice category
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

    /**
     * Gets the relevance of the invoice for the annual statement.
     *
     * @return true if the invoice is relevant for the annual statement, false otherwise
     */
    public boolean isRelevantForAnnualStatement() {
        return relevantForAnnualStatement;
    }

    /**
     * Sets the relevance of the invoice for the annual statement.
     *
     * @param relevantForAnnualStatement true if the invoice is relevant for the annual statement, false otherwise
     */
    public void setRelevantForAnnualStatement(boolean relevantForAnnualStatement) {
        this.relevantForAnnualStatement = relevantForAnnualStatement;
    }
}
/**
 * End
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */