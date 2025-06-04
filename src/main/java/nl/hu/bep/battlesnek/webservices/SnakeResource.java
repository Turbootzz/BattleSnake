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
import java.util.HashMap;
import java.util.Map;

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
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(GameState gameState) {
        System.out.println(gameState.getGame().getId());
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(String requestBody) {
        try {
            Map<String, String> move = new HashMap<>();
            move.put("move", "right");

            return Response.ok(move).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/end")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endGame(GameState gameState) {
        System.out.println("Game ended: " + gameState.getGame().getId());
        return Response.ok().build();
    }
}
