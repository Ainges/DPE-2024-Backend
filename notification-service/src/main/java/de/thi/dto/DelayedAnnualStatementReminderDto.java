package de.thi.dto;

public class DelayedAnnualStatementReminderDto {
    /**
     * The code defines a class DelayedAnnualStatementReminderDto with several private fields representing delayed annual statement reminder details
     * Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.
     */
    private String mailType;
    private AnnualStatementPreparationReminderDto.DataDTO data;

    public DelayedAnnualStatementReminderDto() {
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

    public AnnualStatementPreparationReminderDto.DataDTO getData() {
        return data;
    }

    public void setData(AnnualStatementPreparationReminderDto.DataDTO data) {
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


        /**
         * Defines a constructor that initializes all fields of the class
         * The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
         * This constructor initializes the private fields of the class with the provided parameters.
         * Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
         */
        public DataDTO() {
        }

        // Getters and Setters


        public String getHousingObjectId() {
            return housingObjectId;
        }

        public void setHousingObjectId(String housingObjectId) {
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
}
