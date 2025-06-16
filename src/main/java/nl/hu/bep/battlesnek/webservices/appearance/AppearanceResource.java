package nl.hu.bep.battlesnek.webservices.appearance;

import nl.hu.bep.battlesnek.model.SnakeAppearance;
import nl.hu.bep.battlesnek.persistence.PersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/appearance")
public class AppearanceResource implements Serializable {
    // Seperate static instance of the appearance
    public static SnakeAppearance getCurrentAppearance() {
        return PersistenceManager.getAppearance();
    }

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppearance() {
        return Response.ok(getCurrentAppearance()).build();
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppearance(AppearanceDTO dto) {
        // Update saved appearance with new data
        getCurrentAppearance().setColor(dto.color);
        getCurrentAppearance().setHead(dto.head);
        getCurrentAppearance().setTail(dto.tail);
        PersistenceManager.saveDataToFile(getCurrentAppearance());

        return Response.ok().build();
    }
}
