/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package entity;

import jakarta.persistence.*;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "Tenant_RentalAgreement",
            joinColumns = @JoinColumn(name = "tenantId"),
            inverseJoinColumns = @JoinColumn(name = "rentalAgreementId")
    )
    private Set<RentalAgreement> rentalAgreements;

    // Default constructor
    public Tenant() {
    }

    // Constructor without rental agreements
    public Tenant(String firstName, String lastName, String email, String phoneNumber, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
    }

    // Constructor with rental agreements
    public Tenant(String firstName, String lastName, String email, String phoneNumber, boolean active, Set<RentalAgreement> rentalAgreements) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.rentalAgreements = rentalAgreements;
    }

    // Getters and Setters
    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(Set<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */