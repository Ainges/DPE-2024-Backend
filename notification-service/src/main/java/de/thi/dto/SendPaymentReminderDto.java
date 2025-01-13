package de.thi.dto;

public class SendPaymentReminderDto {

    private String mailType;
    private DataDTO data;

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

        public DataDTO(String receiver, String receiverIban, double invoiceAmount, String description, String country, String currency, String bic) {
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
