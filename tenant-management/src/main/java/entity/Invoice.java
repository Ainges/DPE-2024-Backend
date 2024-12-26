/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */

package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;

    private Date invoiceDate;
    private float invoiceAmount;

    @ManyToOne
    @JoinColumn(name = "invoiceCategoryId")
    private InvoiceCategory invoiceCategory;

    @ManyToOne
    @JoinColumn(name = "housingObjectId")
    private HousingObject housingObject;

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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

    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public HousingObject getHousingObject() {
        return housingObject;
    }

    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }
}
/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */