package de.thi.dto;

public class AnnualStatementPreparationReminderDto {

    private String mailType;
    private DataDTO data;

    public AnnualStatementPreparationReminderDto() {
    }

    // Getters and Setters
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

    // Nested DataDTO class
    public static class DataDTO {

        private String name;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private int numberOfApartments;

        public DataDTO() {
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


