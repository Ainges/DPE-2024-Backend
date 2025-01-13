package de.thi.dto;

public class QRCodePaymentDTO {

    private String mailType;
    private Data data;

    public QRCodePaymentDTO() {
    }

    public QRCodePaymentDTO(String mailType, Data data) {
        this.mailType = mailType;
        this.data = data;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private PaymentDetails objPaymentDetails;
        private String base64QR;

        public Data() {
        }

        public Data(PaymentDetails objPaymentDetails, String base64QR) {
            this.objPaymentDetails = objPaymentDetails;
            this.base64QR = base64QR;
        }

        public PaymentDetails getObjPaymentDetails() {
            return objPaymentDetails;
        }

        public void setObjPaymentDetails(PaymentDetails objPaymentDetails) {
            this.objPaymentDetails = objPaymentDetails;
        }

        public String getBase64QR() {
            return base64QR;
        }

        public void setBase64QR(String base64QR) {
            this.base64QR = base64QR;
        }
    }

    public static class PaymentDetails {
        private String receiver;
        private String receiverIban;
        private double invoiceAmount;
        private String description;
        private String country;
        private String currency;
        private String bic;

        public PaymentDetails() {
        }

        public PaymentDetails(String receiver, String receiverIban, double invoiceAmount, String description, String country, String currency, String bic) {
            this.receiver = receiver;
            this.receiverIban = receiverIban;
            this.invoiceAmount = invoiceAmount;
            this.description = description;
            this.country = country;
            this.currency = currency;
            this.bic = bic;
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

