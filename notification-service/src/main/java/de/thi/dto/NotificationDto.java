package de.thi.dto;

/**
 * The code defines a class NotificationDto with several private fields representing notification details
 * Each field is annotated with @JacksonXmlProperty to specify the XML element name when the object is serialized to XML.
 */
public class NotificationDto {
    private String mailType;
    private String payload;


    public NotificationDto() {
    }
    /**
     * Defines a constructor that initializes all fields of the class
     * The constructor is called when creating a new instance of the class and sets the values of the fields based on the provided parameters.
     * This constructor initializes the private fields of the class with the provided parameters.
     * Each parameter corresponds to a field in the class, and the constructor assigns the parameter values to the respective fields.
     */
    public NotificationDto(String type, String payload) {
        this.mailType = type;
        this.payload = payload;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
