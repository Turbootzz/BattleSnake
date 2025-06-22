package nl.hu.bep.battlesnek.webservices.feedback;

public class FeedbackDTO {
    public String message;
    public String username;
    public String timestamp;

    public FeedbackDTO(String message, String username, String timestamp) {
        this.message = message;
        this.username = username;
        this.timestamp = timestamp;
    }

    public FeedbackDTO() {}
}
