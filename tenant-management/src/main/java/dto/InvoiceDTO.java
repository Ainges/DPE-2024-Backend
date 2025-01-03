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
     * Gets the amount of the invoice.
     *
     * @return the amount of the invoice
     */
    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Sets the amount of the invoice.
     *
     * @param invoiceAmount the amount of the invoice
     */
    public void setInvoiceAmount(double invoiceAmount) {
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
     * Gets the country code of the invoice.
     *
     * @return the country code of the invoice
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country code of the invoice.
     *
     * @param country the country code of the invoice
     */
    public void setCountry(String country) {
        this.country = country;
    }
}