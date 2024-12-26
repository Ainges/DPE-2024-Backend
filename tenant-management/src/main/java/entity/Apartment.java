/**
 * Start
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */
package entity;

import jakarta.persistence.*;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long apartmentId;

    @ManyToOne
    @JoinColumn(name = "housingObjectId")
    private HousingObject housingObject;

    private float areaInM2;
    private int numberOfRooms;
    private float coldRent;
    private float heatingCostPrepayment;
    private float additionalCostPrepayment;

    // Getters and Setters
    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public HousingObject getHousingObject() {
        return housingObject;
    }

    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }

    public float getAreaInM2() {
        return areaInM2;
    }

    public void setAreaInM2(float areaInM2) {
        this.areaInM2 = areaInM2;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public float getColdRent() {
        return coldRent;
    }

    public void setColdRent(float coldRent) {
        this.coldRent = coldRent;
    }

    public float getHeatingCostPrepayment() {
        return heatingCostPrepayment;
    }

    public void setHeatingCostPrepayment(float heatingCostPrepayment) {
        this.heatingCostPrepayment = heatingCostPrepayment;
    }

    public float getAdditionalCostPrepayment() {
        return additionalCostPrepayment;
    }

    public void setAdditionalCostPrepayment(float additionalCostPrepayment) {
        this.additionalCostPrepayment = additionalCostPrepayment;
    }
}

/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */