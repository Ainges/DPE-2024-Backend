package dto;
//The line import java.util.Date;
// imports the Date class from the java.util package.
// This allows the InvoiceDTO class to use the Date class for its invoiceDate attribute.
// The Date class represents a specific instant in time, with millisecond precision.

import java.util.Date;
//defines the InvoiceDTO class with several private fields and a no-argument constructor
//The no-argument constructor initializes an instance of the InvoiceDTO class without setting any field values.
public class InvoiceDTO {

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

    public InvoiceDTO() {
        // no Args
    }
//defines a constructor for the InvoiceDTO class.
// This constructor takes several parameters and initializes the corresponding fields of the InvoiceDTO object with the provided values
    //Parameters: The constructor accepts parameters for each field in the InvoiceDTO class, including invoiceDate, invoiceAmount, description, status, receiver, receiverIban, receiverBic, externalInvoiceNumber, invoiceCategoryId, housingObjectId, currency, and relevantForAnnualStatement.
//Initialization: Each parameter is assigned to the corresponding private field of the class using the this keyword.
    //The constructor allows creating an InvoiceDTO object with all its fields initialized to specific values at the time of creation.
    public InvoiceDTO(Date invoiceDate, float invoiceAmount, String description, String status, String receiver, String receiverIban, String receiverBic, String externalInvoiceNumber, String invoiceCategoryId, String housingObjectId, String currency, String relevantForAnnualStatement) {
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
    //The selected code defines the getter and setter methods for each private field in the InvoiceDTO class.
    // These methods allow for accessing and modifying the values of the private fields from outside the class
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