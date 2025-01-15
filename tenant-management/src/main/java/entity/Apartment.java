/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import jakarta.persistence.*;

/**
 * Represents an apartment entity.
 * Represents an annual statement for a rental agreement.
 *  * @Entity: Marks the class as a JPA entity.
 *  * @Id: Specifies the primary key of the entity.
 *  * @GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value is automatically generated.
 *  * private long apartmentId: The primary key field.
 *  * @ManyToOne: Specifies a many-to-one relationship with another entity.
 *  * @JoinColumn(name = "housingObjectId"): Specifies the foreign key column.
 *  *
 */
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

    /**
     * Default constructor.
     */
    public Apartment() {
    }

    /**
     * Constructor with parameters.
     *
     * @param housingObject            the housing object associated with this apartment
     * @param areaInM2                 the area of the apartment in square meters
     * @param numberOfRooms            the number of rooms in the apartment
     * @param coldRent                 the cold rent of the apartment
     * @param heatingCostPrepayment    the heating cost prepayment for the apartment
     * @param additionalCostPrepayment the additional cost prepayment for the apartment
     */
    public Apartment(HousingObject housingObject, float areaInM2, int numberOfRooms, float coldRent, float heatingCostPrepayment, float additionalCostPrepayment) {
        this.housingObject = housingObject;
        this.areaInM2 = areaInM2;
        this.numberOfRooms = numberOfRooms;
        this.coldRent = coldRent;
        this.heatingCostPrepayment = heatingCostPrepayment;
        this.additionalCostPrepayment = additionalCostPrepayment;
    }

    // Getters and Setters

    /**
     * Gets the ID of the apartment.
     *
     * @return the ID of the apartment
     */
    public long getApartmentId() {
        return apartmentId;
    }

    /**
     * Sets the ID of the apartment.
     *
     * @param apartmentId the ID of the apartment
     */
    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    /**
     * Gets the housing object associated with this apartment.
     *
     * @return the housing object
     */
    public HousingObject getHousingObject() {
        return housingObject;
    }

    /**
     * Sets the housing object associated with this apartment.
     *
     * @param housingObject the housing object
     */
    public void setHousingObject(HousingObject housingObject) {
        this.housingObject = housingObject;
    }

    /**
     * Gets the area of the apartment in square meters.
     *
     * @return the area of the apartment in square meters
     */
    public float getAreaInM2() {
        return areaInM2;
    }

    /**
     * Sets the area of the apartment in square meters.
     *
     * @param areaInM2 the area of the apartment in square meters
     */
    public void setAreaInM2(float areaInM2) {
        this.areaInM2 = areaInM2;
    }

    /**
     * Gets the number of rooms in the apartment.
     *
     * @return the number of rooms in the apartment
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Sets the number of rooms in the apartment.
     *
     * @param numberOfRooms the number of rooms in the apartment
     */
    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Gets the cold rent of the apartment.
     *
     * @return the cold rent of the apartment
     */
    public float getColdRent() {
        return coldRent;
    }

    /**
     * Sets the cold rent of the apartment.
     *
     * @param coldRent the cold rent of the apartment
     */
    public void setColdRent(float coldRent) {
        this.coldRent = coldRent;
    }

    /**
     * Gets the heating cost prepayment for the apartment.
     *
     * @return the heating cost prepayment for the apartment
     */
    public float getHeatingCostPrepayment() {
        return heatingCostPrepayment;
    }

    /**
     * Sets the heating cost prepayment for the apartment.
     *
     * @param heatingCostPrepayment the heating cost prepayment for the apartment
     */
    public void setHeatingCostPrepayment(float heatingCostPrepayment) {
        this.heatingCostPrepayment = heatingCostPrepayment;
    }

    /**
     * Gets the additional cost prepayment for the apartment.
     *
     * @return the additional cost prepayment for the apartment
     */
    public float getAdditionalCostPrepayment() {
        return additionalCostPrepayment;
    }

    /**
     * Sets the additional cost prepayment for the apartment.
     *
     * @param additionalCostPrepayment the additional cost prepayment for the apartment
     */
    public void setAdditionalCostPrepayment(float additionalCostPrepayment) {
        this.additionalCostPrepayment = additionalCostPrepayment;
    }
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */