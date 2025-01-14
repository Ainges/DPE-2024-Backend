package dto;

import java.util.Date;

public class InvoiceCreateDto {

    private Date invoiceDate;
    private float invoiceAmount;
    private String description;
    private String status;
    private String receiver;
    private String receiverIban;
    private String receiverBic;
    private String externalInvoiceNumber;
    private String invoiceCategoryId;
    private String housingObjectId;
    private String currency; // New attribute
    private String relevantForAnnualStatement;

    public InvoiceCreateDto() {
        // no Args
    }

    public InvoiceCreateDto(Date invoiceDate, float invoiceAmount, String description, String status, String receiver, String receiverIban, String receiverBic, String externalInvoiceNumber, String invoiceCategoryId, String housingObjectId, String currency, String relevantForAnnualStatement) {
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.description = description;
        this.status = status;
        this.receiver = receiver;
        this.receiverIban = receiverIban;
        this.receiverBic = receiverBic;
        this.externalInvoiceNumber = externalInvoiceNumber;
        this.invoiceCategoryId = invoiceCategoryId;
        this.housingObjectId = housingObjectId;
        this.currency = currency;
        this.relevantForAnnualStatement = relevantForAnnualStatement;
    }

    // Getters and Setters
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getReceiverBic() {
        return receiverBic;
    }

    public void setReceiverBic(String receiverBic) {
        this.receiverBic = receiverBic;
    }

    public String getExternalInvoiceNumber() {
        return externalInvoiceNumber;
    }

    public void setExternalInvoiceNumber(String externalInvoiceNumber) {
        this.externalInvoiceNumber = externalInvoiceNumber;
    }

    public String getInvoiceCategoryId() {
        return invoiceCategoryId;
    }

    public void setInvoiceCategoryId(String invoiceCategoryId) {
        this.invoiceCategoryId = invoiceCategoryId;
    }

    public String getHousingObjectId() {
        return housingObjectId;
    }

    public void setHousingObjectId(String housingObjectId) {
        this.housingObjectId = housingObjectId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRelevantForAnnualStatement() {
        return relevantForAnnualStatement;
    }

    public void setRelevantForAnnualStatement(String relevantForAnnualStatement) {
        this.relevantForAnnualStatement = relevantForAnnualStatement;
    }
}