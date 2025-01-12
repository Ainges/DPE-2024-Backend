package de.thi.dto;

public class NotificationDto {
    private String mailType;
    private String payload;

    public NotificationDto() {
    }

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
