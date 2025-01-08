/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur, Zohal Mohammadi
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
    private String country;
    private String currency = "EUR"; // Default currency
    private String bic; // Optional

    // Getters and setters for all attributes

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur, Zohal Mohammadi
 */