package nl.hu.bep.battlesnek.webservices.authentication;

import nl.hu.bep.battlesnek.model.MyUser;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/user")
public class UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getCurrentUser(@Context SecurityContext sc) {
        MyUser user = (MyUser) sc.getUserPrincipal();
        UserDTO dto = new UserDTO(user.getName(), user.getRole());
        return Response.ok(dto).build();
    }
}
