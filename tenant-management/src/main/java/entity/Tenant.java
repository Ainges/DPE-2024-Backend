/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Represents a tenant entity.
 */
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tenantId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean active;

    /**
     * Rental Agreement is the owner of the m:n relationship with Tenant.
     * Therefore, the relationships should be created, when the Rental Agreement is created.
     */
    @ManyToMany(mappedBy = "tenants")
    @JsonIgnore
    private Set<RentalAgreement> rentalAgreements;

    /**
     * Default constructor.
     */
    public Tenant() {
    }

    /**
     * Constructor without rental agreements.
     *
     * @param firstName   the first name of the tenant
     * @param lastName    the last name of the tenant
     * @param email       the email address of the tenant
     * @param phoneNumber the phone number of the tenant
     * @param active      the active status of the tenant
     */
    public Tenant(String firstName, String lastName, String email, String phoneNumber, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
    }

    /**
     * Constructor with rental agreements.
     *
     * @param firstName        the first name of the tenant
     * @param lastName         the last name of the tenant
     * @param email            the email address of the tenant
     * @param phoneNumber      the phone number of the tenant
     * @param active           the active status of the tenant
     * @param rentalAgreements the set of rental agreements associated with this tenant
     */
    public Tenant(String firstName, String lastName, String email, String phoneNumber, boolean active, Set<RentalAgreement> rentalAgreements) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.rentalAgreements = rentalAgreements;
    }

    // Getters and Setters

    /**
     * Gets the ID of the tenant.
     *
     * @return the ID of the tenant
     */
    public long getTenantId() {
        return tenantId;
    }

    /**
     * Sets the ID of the tenant.
     *
     * @param tenantId the ID of the tenant
     */
    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Gets the first name of the tenant.
     *
     * @return the first name of the tenant
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the tenant.
     *
     * @param firstName the first name of the tenant
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the tenant.
     *
     * @return the last name of the tenant
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the tenant.
     *
     * @param lastName the last name of the tenant
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the tenant.
     *
     * @return the email address of the tenant
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the tenant.
     *
     * @param email the email address of the tenant
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the tenant.
     *
     * @return the phone number of the tenant
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the tenant.
     *
     * @param phoneNumber the phone number of the tenant
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the active status of the tenant.
     *
     * @return the active status of the tenant
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the tenant.
     *
     * @param active the active status of the tenant
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the set of rental agreements associated with this tenant.
     *
     * @return the set of rental agreements
     */
    public Set<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    /**
     * Sets the set of rental agreements associated with this tenant.
     *
     * @param rentalAgreements the set of rental agreements
     */
    public void setRentalAgreements(Set<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */