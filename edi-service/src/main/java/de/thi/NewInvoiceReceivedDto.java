package de.thi;

public class NewInvoiceReceivedDto {

    public String invoiceId;
    public boolean digitalInvoice;

    public NewInvoiceReceivedDto() {
    }

    public NewInvoiceReceivedDto(String invoiceId, boolean digitalInvoice) {
        this.invoiceId = invoiceId;
        this.digitalInvoice = digitalInvoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public boolean isDigitalInvoice() {
        return digitalInvoice;
    }

    public void setDigitalInvoice(boolean digitalInvoice) {
        this.digitalInvoice = digitalInvoice;
    }
}
