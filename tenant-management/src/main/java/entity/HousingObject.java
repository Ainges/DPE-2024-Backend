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

@Entity
public class HousingObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int housingObjectId;

    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private int numberOfApartments;

    public HousingObject() {
    }

    public HousingObject(int housingObjectId, String name, String street, String city, String state, String zipCode, int numberOfApartments) {
        this.housingObjectId = housingObjectId;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.numberOfApartments = numberOfApartments;
    }

    public int getHousingObjectId() {
        return housingObjectId;
    }

    public void setHousingObjectId(int housingObjectId) {
        this.housingObjectId = housingObjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getNumberOfApartments() {
        return numberOfApartments;
    }

    public void setNumberOfApartments(int numberOfApartments) {
        this.numberOfApartments = numberOfApartments;
    }
}
/**
 * End
 * Primary @author GitHub Copilot
 * Secondary @author Moritz Baur
 */