package de.thi.dto;


import java.util.List;

public class AnnualStatementNotificationDto {

    private String mailType;
    private AnnualStatementDTO data;

    // Getters and Setters
    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public AnnualStatementDTO getData() {
        return data;
    }

    public void setData(AnnualStatementDTO data) {
        this.data = data;
    }

    public static class AnnualStatementDTO {

        private long annualStatementId;
        private RentalAgreementDTO rentalAgreement;
        private String periodStart;
        private String periodEnd;
        private double totalCost;
        private double totalPrepayments;
        private double difference;

        // Getters and Setters
        public long getAnnualStatementId() {
            return annualStatementId;
        }

        public void setAnnualStatementId(long annualStatementId) {
            this.annualStatementId = annualStatementId;
        }

        public RentalAgreementDTO getRentalAgreement() {
            return rentalAgreement;
        }

        public void setRentalAgreement(RentalAgreementDTO rentalAgreement) {
            this.rentalAgreement = rentalAgreement;
        }

        public String getPeriodStart() {
            return periodStart;
        }

        public void setPeriodStart(String periodStart) {
            this.periodStart = periodStart;
        }

        public String getPeriodEnd() {
            return periodEnd;
        }

        public void setPeriodEnd(String periodEnd) {
            this.periodEnd = periodEnd;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }

        public double getTotalPrepayments() {
            return totalPrepayments;
        }

        public void setTotalPrepayments(double totalPrepayments) {
            this.totalPrepayments = totalPrepayments;
        }

        public double getDifference() {
            return difference;
        }

        public void setDifference(double difference) {
            this.difference = difference;
        }

        public static class RentalAgreementDTO {

            private long rentalAgreementId;
            private List<TenantDTO> tenants;
            private ApartmentDTO apartment;
            private String startDate;
            private String endDate;

            // Getters and Setters
            public long getRentalAgreementId() {
                return rentalAgreementId;
            }

            public void setRentalAgreementId(long rentalAgreementId) {
                this.rentalAgreementId = rentalAgreementId;
            }

            public List<TenantDTO> getTenants() {
                return tenants;
            }

            public void setTenants(List<TenantDTO> tenants) {
                this.tenants = tenants;
            }

            public ApartmentDTO getApartment() {
                return apartment;
            }

            public void setApartment(ApartmentDTO apartment) {
                this.apartment = apartment;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }
        }

        public static class TenantDTO {

            private long tenantId;
            private String firstName;
            private String lastName;
            private String email;
            private String phoneNumber;
            private boolean active;

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
        }

        public static class ApartmentDTO {

            private long apartmentId;
            private HousingObjectDTO housingObject;
            private double areaInM2;
            private int numberOfRooms;
            private double coldRent;
            private double heatingCostPrepayment;
            private double additionalCostPrepayment;

            // Getters and Setters
            public long getApartmentId() {
                return apartmentId;
            }

            public void setApartmentId(long apartmentId) {
                this.apartmentId = apartmentId;
            }

            public HousingObjectDTO getHousingObject() {
                return housingObject;
            }

            public void setHousingObject(HousingObjectDTO housingObject) {
                this.housingObject = housingObject;
            }

            public double getAreaInM2() {
                return areaInM2;
            }

            public void setAreaInM2(double areaInM2) {
                this.areaInM2 = areaInM2;
            }

            public int getNumberOfRooms() {
                return numberOfRooms;
            }

            public void setNumberOfRooms(int numberOfRooms) {
                this.numberOfRooms = numberOfRooms;
            }

            public double getColdRent() {
                return coldRent;
            }

            public void setColdRent(double coldRent) {
                this.coldRent = coldRent;
            }

            public double getHeatingCostPrepayment() {
                return heatingCostPrepayment;
            }

            public void setHeatingCostPrepayment(double heatingCostPrepayment) {
                this.heatingCostPrepayment = heatingCostPrepayment;
            }

            public double getAdditionalCostPrepayment() {
                return additionalCostPrepayment;
            }

            public void setAdditionalCostPrepayment(double additionalCostPrepayment) {
                this.additionalCostPrepayment = additionalCostPrepayment;
            }
        }

        public static class HousingObjectDTO {

            private long housingObjectId;
            private String name;
            private String street;
            private String city;
            private String state;
            private String zipCode;
            private int numberOfApartments;

            // Getters and Setters
            public long getHousingObjectId() {
                return housingObjectId;
            }

            public void setHousingObjectId(long housingObjectId) {
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
}
