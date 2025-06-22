package nl.hu.bep.battlesnek.webservices.feedback;

public class FeedbackDTO {
    public String message;
    public String username;

    public FeedbackDTO(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public FeedbackDTO() {}
}
