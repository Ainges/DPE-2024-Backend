/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentalAgreementId;

    // Rental Agreement is the owner of the m:n relationship with Tenant.
    // Therefore, the relationships should be created, when the Rental Agreement is created.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Tenant_RentalAgreement",
            joinColumns = @JoinColumn(name = "rentalAgreementId"),
            inverseJoinColumns = @JoinColumn(name = "tenantId")
    )
    private Set<Tenant> tenants;

    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;

    private Date startDate;
    private Date endDate;

    // Default constructor
    public RentalAgreement() {
    }

    // Constructor without tenants
    public RentalAgreement(Apartment apartment, Date startDate, Date endDate) {
        this.apartment = apartment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Constructor with tenants
    public RentalAgreement(Set<Tenant> tenants, Apartment apartment, Date startDate, Date endDate) {
        this.tenants = tenants;
        this.apartment = apartment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public long getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(long rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */