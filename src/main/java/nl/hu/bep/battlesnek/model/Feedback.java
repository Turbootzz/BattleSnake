package nl.hu.bep.battlesnek.model;

import java.time.LocalDateTime;

public class Feedback {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String username;
    private LocalDateTime submittedAt;

    public Feedback(String message, String username, LocalDateTime submittedAt) {
        this.message = message;
        this.username = username;
        this.submittedAt = submittedAt;
    }
}
