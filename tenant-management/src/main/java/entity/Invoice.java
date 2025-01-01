/**
 * Start
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
     * @param status          the status of the invoice e.g. paid, unpaid
     * @param receiver        the receiver of the invoice
     * @param receiverIban    the IBAN of the receiver
     * @param receiverBic     the BIC of the receiver
     * @param invoiceCategory the category of the invoice
     * @param housingObject   the housing object associated with the invoice
     */
    public Invoice(Date invoiceDate, float invoiceAmount, String description, String status, String receiver, String receiverIban, String receiverBic, InvoiceCategory invoiceCategory, HousingObject housingObject) {
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.description = description;
        this.status = status;
        this.receiver = receiver;
        this.receiverIban = receiverIban;
        this.receiverBic = receiverBic;
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

    /**
     * Gets the receiver of the invoice.
     *
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the invoice.
     *
     * @param receiver the receiver
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
}
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */