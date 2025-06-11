package nl.hu.bep.battlesnek.webservices.appearance;

import nl.hu.bep.battlesnek.model.SnakeAppearance;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/appearance")
public class AppearanceResource {

    // Seperate static instance to temporarily save the snake config
    private static SnakeAppearance appearance = new SnakeAppearance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppearance() {
        return Response.ok(appearance).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppearance(AppearanceDTO dto) {
        // Update saved appearance with new data
        appearance.setColor(dto.color);
        appearance.setHead(dto.head);
        appearance.setTail(dto.tail);

        return Response.ok().build();
    }

    public static SnakeAppearance getCurrentAppearance() {
        return appearance;
    }
}
