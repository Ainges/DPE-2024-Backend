package de.thi.dto;

// Author: Hubertus Seitz & ChatGPT

/**
 * The code defines a class SendPaymentReminderDto with several private fields representing payment reminder details
 * Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.
 */
public class SendPaymentReminderDto {


    private String mailType;
    private DataDTO data;

    /**
     * Defines a constructor that initializes all fields of the class
     * The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
     * This constructor initializes the private fields of the class with the provided parameters.
     * Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
     */
    public SendPaymentReminderDto() {
    }

    public String getMailType() {
        return mailType;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    /**
     * Defines inner static class DataDTO with different properties.
     * Each Property is displayed in the XML file as specified in the @JacksonXmlProperty
     * The properties include receiver, receiverIban, invoiceAmount, description, country, currency, and bic.
     * These annotations ensure that when an instance of DataDTO is serialized to XML, the properties will be displayed with the specified local names
     */
    public static class DataDTO {

        private String receiver;
        private String receiverIban;
        private double invoiceAmount;
        private String description;
        private String country;
        private String currency;
        private String bic;

        public DataDTO() {
        }
        /**
         * Defines a constructor that initializes all fields of the class
         * The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
         * This constructor initializes the private fields of the class with the provided parameters.
         * Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
         */
        public DataDTO(String receiver, String receiverIban, double invoiceAmount, String description, String country, String currency, String bic) {
            this.receiver = receiver;
            this.receiverIban = receiverIban;
            this.invoiceAmount = invoiceAmount;
            this.description = description;
            this.country = country;
            this.currency = currency;
            this.bic = bic;
        }
        /**
         * Defines Getter and Setter for receiver, receiverIban, invoiceAmount, description, country, currency, and bic. Enables access and modification of values
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

}
