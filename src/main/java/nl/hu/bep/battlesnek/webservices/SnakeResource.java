package nl.hu.bep.battlesnek.webservices;

import nl.hu.bep.battlesnek.model.Snake;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("snake")
public class SnakeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSnake() {
        Snake snake = new Snake();
        //return "{\"x\":0,\"y\":0}";
        return Response.ok(snake).build().toString();
    }
}
