/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package entity;

import jakarta.persistence.*;

/**
 * Represents a housing object entity.
 */
@Entity
public class HousingObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long housingObjectId;

    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private int numberOfApartments;

    /**
     * Default constructor.
     */
    public HousingObject() {
    }

    /**
     * Constructor with parameters.
     *
     * @param name               the name of the housing object
     * @param street             the street address of the housing object
     * @param city               the city where the housing object is located
     * @param state              the state where the housing object is located
     * @param zipCode            the zip code of the housing object
     * @param numberOfApartments the number of apartments in the housing object
     */
    public HousingObject(String name, String street, String city, String state, String zipCode, int numberOfApartments) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.numberOfApartments = numberOfApartments;
    }

    /**
     * Gets the ID of the housing object.
     *
     * @return the ID of the housing object
     */
    public long getHousingObjectId() {
        return housingObjectId;
    }

    /**
     * Sets the ID of the housing object.
     *
     * @param housingObjectId the ID of the housing object
     */
    public void setHousingObjectId(int housingObjectId) {
        this.housingObjectId = housingObjectId;
    }

    /**
     * Gets the name of the housing object.
     *
     * @return the name of the housing object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the housing object.
     *
     * @param name the name of the housing object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the street address of the housing object.
     *
     * @return the street address of the housing object
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address of the housing object.
     *
     * @param street the street address of the housing object
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the city where the housing object is located.
     *
     * @return the city where the housing object is located
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the housing object is located.
     *
     * @param city the city where the housing object is located
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state where the housing object is located.
     *
     * @return the state where the housing object is located
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state where the housing object is located.
     *
     * @param state the state where the housing object is located
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the zip code of the housing object.
     *
     * @return the zip code of the housing object
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code of the housing object.
     *
     * @param zipCode the zip code of the housing object
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the number of apartments in the housing object.
     *
     * @return the number of apartments in the housing object
     */
    public int getNumberOfApartments() {
        return numberOfApartments;
    }

    /**
     * Sets the number of apartments in the housing object.
     *
     * @param numberOfApartments the number of apartments in the housing object
     */
    public void setNumberOfApartments(int numberOfApartments) {
        this.numberOfApartments = numberOfApartments;
    }
}

/**
 * End
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */