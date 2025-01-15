package de.thi;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.util.Date;
//The code defines a class InvoiceCreateDto with several private fields representing invoice details
//Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.

public class InvoiceCreateDto {

    @JacksonXmlProperty(localName = "InvoiceDate")
    private Date invoiceDate;
    @JacksonXmlProperty(localName = "InvoiceAmount")
    private float invoiceAmount;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "Status")
    private String status;
    @JacksonXmlProperty(localName = "Receiver")
    private String receiver;
    @JacksonXmlProperty(localName = "ReceiverIBAN")
    private String receiverIban;
    @JacksonXmlProperty(localName = "ReceiverBIC")
    private String receiverBic;

    //Defines a constructor that initializes all fields of the class
    //The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
    //This constructor initializes the private fields of the class with the provided parameters.
    // Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
    public InvoiceCreateDto(Date invoiceDate, float invoiceAmount, String description, String status, String receiver, String receiverIban, String receiverBic) {
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
        this.description = description;
        this.status = status;
        this.receiver = receiver;
        this.receiverIban = receiverIban;
        this.receiverBic = receiverBic;
    }

    public InvoiceCreateDto() {
    }

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
}
