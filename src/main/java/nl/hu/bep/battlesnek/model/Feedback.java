package nl.hu.bep.battlesnek.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Feedback implements Serializable {
    private String message;
    private String username;
    private String submittedAt;

    public Feedback(String message, String username, String submittedAt) {
        this.message = message;
        this.username = username;
        this.submittedAt = submittedAt;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LocalDateTime getSubmittedAt() {
        return LocalDateTime.parse(submittedAt);
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt.toString();
    }
}
