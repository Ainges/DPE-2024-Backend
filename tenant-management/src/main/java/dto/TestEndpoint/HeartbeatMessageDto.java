package dto.TestEndpoint;

public class HeartbeatMessageDto {

    private String message;

    public HeartbeatMessageDto() {
    }

    public HeartbeatMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
