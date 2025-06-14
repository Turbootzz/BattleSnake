package nl.hu.bep.battlesnek.security;

import nl.hu.bep.battlesnek.model.MyUser;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private final MyUser user;
    private final String scheme;

    public MySecurityContext(MyUser user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }
    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }
    @Override
    public boolean isUserInRole(String role) {
        return user != null && user.getRole().equals(role);
    }
    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
