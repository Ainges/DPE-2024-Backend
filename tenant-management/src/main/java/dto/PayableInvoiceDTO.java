/**
 * @author 1 Zohal Mohammadi, Moritz Baur
 * @author 2 GitHub Copilot
 */
package dto;

/**
 * Data Transfer Object (DTO) for Invoice.
 * This class represents the data structure for an invoice, including the receiver's details,
 * the invoice amount, and a description of the invoice.
 */
public class PayableInvoiceDTO {
    private String receiver;
    private String receiverIban;
    private double invoiceAmount;
    private String description;
    private String currency;
    private String bic; // Optional

    /**
     * Getters and setters for all attributes
     * contains the getter and setter methods for the PayableInvoiceDTO class attributes.
     * These methods allow for accessing and modifying the private fields of the class.Default constructor.
     */
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverIban() {
        return receiverIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}