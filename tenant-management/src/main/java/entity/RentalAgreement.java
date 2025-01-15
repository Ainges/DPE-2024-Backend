/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

/**
 * Represents a rental agreement entity.
 */
@Entity
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentalAgreementId;

    /**
     * Rental Agreement is the owner of the m:n relationship with Tenant.
     * Therefore, the relationships should be created, when the Rental Agreement is created.
     */
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

    /**
     * Default constructor.
     */
    public RentalAgreement() {
    }

    /**
     * Constructor without tenants.
     *
     * @param apartment the apartment associated with this rental agreement
     * @param startDate the start date of the rental agreement
     * @param endDate   the end date of the rental agreement
     */
    public RentalAgreement(Apartment apartment, Date startDate, Date endDate) {
        this.apartment = apartment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor with tenants.
     *
     * @param tenants   the set of tenants associated with this rental agreement
     * @param apartment the apartment associated with this rental agreement
     * @param startDate the start date of the rental agreement
     * @param endDate   the end date of the rental agreement
     */
    public RentalAgreement(Set<Tenant> tenants, Apartment apartment, Date startDate, Date endDate) {
        this.tenants = tenants;
        this.apartment = apartment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters

    /**
     * Gets the ID of the rental agreement.
     *
     * @return the ID of the rental agreement
     */
    public long getRentalAgreementId() {
        return rentalAgreementId;
    }

    /**
     * Sets the ID of the rental agreement.
     *
     * @param rentalAgreementId the ID of the rental agreement
     */
    public void setRentalAgreementId(long rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    /**
     * Gets the set of tenants associated with this rental agreement.
     *
     * @return the set of tenants
     */
    public Set<Tenant> getTenants() {
        return tenants;
    }

    /**
     * Sets the set of tenants associated with this rental agreement.
     *
     * @param tenants the set of tenants
     */
    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    /**
     * Gets the apartment associated with this rental agreement.
     *
     * @return the apartment
     */
    public Apartment getApartment() {
        return apartment;
    }

    /**
     * Sets the apartment associated with this rental agreement.
     *
     * @param apartment the apartment
     */
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    /**
     * Gets the start date of the rental agreement.
     *
     * @return the start date of the rental agreement
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the rental agreement.
     *
     * @param startDate the start date of the rental agreement
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the rental agreement.
     *
     * @return the end date of the rental agreement
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the rental agreement.
     *
     * @param endDate the end date of the rental agreement
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

/**
 * End
 * @author 1 Leonie Krauß
 * @author 2 GitHub Copilot
 */