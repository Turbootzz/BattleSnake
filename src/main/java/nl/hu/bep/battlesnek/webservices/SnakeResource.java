package nl.hu.bep.battlesnek.webservices;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.model.Snake;

import javax.json.Json;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("/")
public class SnakeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSnake() {
        Snake snake = new Snake();
        //return "{\"x\":0,\"y\":0}";
        return Response.ok(snake).build().toString();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(GameState gameState) {
        return Response.ok(gameState).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(String requestBody) {
        JsonReader reader = Json.createReader(new StringReader(requestBody));
        try {
            int id = reader.readObject().getInt("id");
            String name = reader.readObject().getString("name");
            int health = reader.readObject().getInt("health");
            Coord body = reader.readObject();
            Coord head = reader.readObject();
            int length = reader.readObject().getInt("length");
            Snake snake = new Snake(id, name, health, body, head, length);
            return Response.ok(snake).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
