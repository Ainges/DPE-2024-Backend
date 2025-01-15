package de.thi.dto;

// Author: Hubertus Seitz & ChatGPT

/**
 * The code defines a class AnnualStatementPreparationReminderDto with several private fields representing annual statement preparation reminder details
 * Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.
 */
public class AnnualStatementPreparationReminderDto {
    /**
     * The code defines a class AnnualStatementPreparationReminderDto with several private fields representing annual statement preparation reminder details
     * Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.
     */
    private String mailType;
    private DataDTO data;

    public AnnualStatementPreparationReminderDto() {
    }

    /**
     * Defines a constructor that initializes all fields of the class
     * The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
     * This constructor initializes the private fields of the class with the provided parameters.
     * Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
     */
    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    /**
     * Defines inner static class DataDTO with different properties.
     * Each Property is displayed in the XML file as specified in the @JacksonXmlProperty
     * The properties include name, street, city, state, zipCode, and numberOfApartments.
     * These annotations ensure that when an instance of DataDTO is serialized to XML, the properties will be displayed with the specified local names
     */
    public static class DataDTO {

        private String housingObjectId;
        private String name;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private int numberOfApartments;

        public DataDTO() {
        }

        public String getHousingObjectId() {
            return housingObjectId;
        }

        public void setHousingObjectId(String housingObjectId) {
            this.housingObjectId = housingObjectId;
        }

        // Getters and Setters
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
}


