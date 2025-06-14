package nl.hu.bep;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import nl.hu.bep.battlesnek.model.MyUser;
import nl.hu.bep.battlesnek.security.LogonRequest;
import nl.hu.bep.battlesnek.webservices.authentication.AuthenticationResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationResourceTest {

    private final AuthenticationResource authResource = new AuthenticationResource();

    @BeforeAll
    static void setup() {
        MyUser.addUser(new MyUser("testuser", "testpassword", "user"));
    }

    @Test
    void testAuthenticateUserWithValidCredentialsShouldReturnOkAndValidJwt() {
        LogonRequest request = new LogonRequest("testuser", "testpassword");
        Response response = authResource.authenticateUser(request);

        // check if status is 200
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // check if body contains token
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getEntity();
        String token = responseBody.get("JWT");
        assertNotNull(token);

        // validate token and check if the claims are correct
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(AuthenticationResource.key).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        assertEquals("testuser", claims.getSubject());
        assertEquals("user", claims.get("role", String.class));
    }

    @Test
    void testAuthenticateUserWithInvalidCredentialsShouldReturnUnauthorized() {
        LogonRequest request = new LogonRequest("testuser", "wrongpassword");
        Response response = authResource.authenticateUser(request);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}