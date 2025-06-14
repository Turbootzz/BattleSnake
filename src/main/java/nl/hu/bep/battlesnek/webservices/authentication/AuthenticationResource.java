package nl.hu.bep.battlesnek.webservices.authentication;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.bep.battlesnek.model.MyUser;
import nl.hu.bep.battlesnek.security.LogonRequest;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Map;

@Path("authentication")
public class AuthenticationResource {
    // if the jwt secret in the .env is NULL then use fallback secret
    public static final String secret = System.getenv().getOrDefault("JWT_SECRET", "dev-secret-key");
    public static final Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest logonRequest) {
        try {
            // Authenticate the user using the credentials provided
            String role = MyUser.validateLogin(logonRequest.username(), logonRequest.password());

            if (role == null) {
                // Throw exception to the catch block
                throw new SecurityException("Invalid username or password");
            }

            // Issue a token for the user
            String token = createToken(logonRequest.username(), role);
            // Return the token on the response
            return Response.ok(Map.of("JWT", token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}