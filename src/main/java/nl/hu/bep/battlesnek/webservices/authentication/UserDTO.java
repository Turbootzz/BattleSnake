package nl.hu.bep.battlesnek.webservices.authentication;

public class UserDTO {
    public String username;
    public String role;

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }
}