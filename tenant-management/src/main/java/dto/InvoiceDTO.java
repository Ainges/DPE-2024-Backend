package dto;

/**
 * Data Transfer Object (DTO) for Invoice.
 * This class represents the data structure for an invoice, including the receiver's details,
 * the invoice amount, and a description of the invoice.
 */
public class InvoiceDTO {
    private String receiver;
    private String receiverIban;
    private double invoiceAmount;
    private String description;
    private String country;
    private String currency = "EUR"; // Default currency
    private String reference; // Optional
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}