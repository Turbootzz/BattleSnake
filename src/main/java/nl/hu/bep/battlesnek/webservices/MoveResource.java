package nl.hu.bep.battlesnek.webservices;

import nl.hu.bep.battlesnek.model.Snake;

import javax.json.Json;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("move")
public class MoveResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(String requestBody) {
        JsonReader reader = Json.createReader(new StringReader(requestBody));
        try {
            int apiVersion = reader.readObject().getInt("apiVersion");
            String author = reader.readObject().getString("author");
            String color = reader.readObject().getString("color");
            String head = reader.readObject().getString("head");
            String tail = reader.readObject().getString("tail");
            float version = reader.readObject().getInt("version");
            Snake snake = new Snake(apiVersion, author, color, head, tail, version);
            return Response.ok(snake).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
