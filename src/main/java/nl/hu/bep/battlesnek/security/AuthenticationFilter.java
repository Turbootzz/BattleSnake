package nl.hu.bep.battlesnek.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import nl.hu.bep.battlesnek.model.MyUser;
import nl.hu.bep.battlesnek.webservices.authentication.AuthenticationResource;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestCtx) {

        String scheme = requestCtx.getUriInfo().getRequestUri().getScheme();
        // Users are treated as guests, unless a valid JWT is provided
        MySecurityContext msc = new MySecurityContext(null, scheme);
        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring("Bearer".length()).trim();

            try {
                // Validate the token
                JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
                Claims claims = parser.parseClaimsJws(token).getBody();

                // Gets username and role from claims
                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                if (username != null && role != null) {
                    // Makes Principal based on the token
                    MyUser principal = new MyUser(username, null, role);
                    msc = new MySecurityContext(principal, scheme);
                }

            } catch (JwtException | IllegalArgumentException e) {
                System.out.println("Invalid JWT, processing as guest!");
            }
        }

        requestCtx.setSecurityContext(msc);
    }
}