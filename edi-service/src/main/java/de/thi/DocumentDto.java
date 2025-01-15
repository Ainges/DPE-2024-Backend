package de.thi;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
// Defines the Java class DocumentDto
public class DocumentDto {
//Shows that invoice is displayed in the XML file as Invoice
    @JacksonXmlProperty(localName = "Invoice")
    private Invoice invoice;
//Shows that digitalInvoice is displayed in the XML file as digitalInvoice
    @JacksonXmlProperty(localName = "digitalInvoice")
    private Boolean digitalInvoice;

    // Defines Getter and Setter for invoice and digitalInvoice. Enables access and modification of values
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Boolean getDigitalInvoice() {
        return digitalInvoice;
    }

    public void setDigitalInvoice(Boolean digitalInvoice) {
        this.digitalInvoice = digitalInvoice;
    }

    // defines inner static class Invoice with different properties.
    // Each Property is displayed in the XML file as specified in the @JacksonXmlProperty
    //The properties include invoiceDate, invoiceAmount, description, status, receiver, receiverIBAN, receiverBIC, currency, and externalInvoiceNumber.
    // These annotations ensure that when an instance of Invoice is serialized to XML, the properties will be displayed with the specified local names
    public static class Invoice {

        @JacksonXmlProperty(localName = "InvoiceDate")
        private String invoiceDate;

        @JacksonXmlProperty(localName = "InvoiceAmount")
        private Double invoiceAmount;

        @JacksonXmlProperty(localName = "Description")
        private String description;

        @JacksonXmlProperty(localName = "Status")
        private String status;

        @JacksonXmlProperty(localName = "Receiver")
        private String receiver;

        @JacksonXmlProperty(localName = "ReceiverIBAN")
        private String receiverIBAN;

        @JacksonXmlProperty(localName = "ReceiverBIC")
        private String receiverBIC;

        @JacksonXmlProperty(localName = "Currency")
        private String currency;

        @JacksonXmlProperty(localName = "ExternalInvoiceNumber")
        private String externalInvoiceNumber;



        // defines getter and setter methods for the properties of the Invoice inner static class within the DocumentDto class
        //these methods enable access and modification of the properties of the Invoice class
        //Each property is annotated with @JacksonXmlProperty to specify how they should be serialized to XML.
        public String getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public Double getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(Double invoiceAmount) {
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

        public String getReceiverIBAN() {
            return receiverIBAN;
        }

        public void setReceiverIBAN(String receiverIBAN) {
            this.receiverIBAN = receiverIBAN;
        }

        public String getReceiverBIC() {
            return receiverBIC;
        }

        public void setReceiverBIC(String receiverBIC) {
            this.receiverBIC = receiverBIC;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getExternalInvoiceNumber() {
            return externalInvoiceNumber;
        }

        public void setExternalInvoiceNumber(String externalInvoiceNumber) {
            this.externalInvoiceNumber = externalInvoiceNumber;
        }
    }
}
