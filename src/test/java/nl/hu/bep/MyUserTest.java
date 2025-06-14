package nl.hu.bep;

import nl.hu.bep.battlesnek.model.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyUserTest {

    @BeforeEach
    void setup() {
        MyUser.addUser(new MyUser("testuser", "testpassword", "user"));
        MyUser.addUser(new MyUser("admin", "adminpass", "admin"));
    }

    @Test
    void testValidateLoginWithCorrectCredentialsShouldReturnRole() {
        String role = MyUser.validateLogin("testuser", "testpassword");
        assertNotNull(role);
        assertEquals("user", role);
    }

    @Test
    void testValidateLoginWithIncorrectPasswordShouldReturnNull() {
        String role = MyUser.validateLogin("testuser", "wrongpassword");
        assertNull(role);
    }

    @Test
    void testValidateLoginWithNonExistentUserShouldReturnNull() {
        String role = MyUser.validateLogin("unknownuser", "somepassword");
        assertNull(role);
    }
}