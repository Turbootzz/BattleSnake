package nl.hu.bep.battlesnek.webservices.snake;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.model.Snake;
import nl.hu.bep.battlesnek.model.SnakeAppearance;
import nl.hu.bep.battlesnek.utils.SnakeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/snake")
public class SnakeResource {
    private static SnakeAppearance appearance = new SnakeAppearance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnake() {
        SnakePositionDTO dto = new SnakePositionDTO(0, 0);
        return Response.ok(appearance).build();
    }

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(GameState gameState) {
        System.out.println(gameState.getGame().getId());
        return Response.ok().build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(GameState gameState) {
        try {
            List<String> possibleMoves = new ArrayList<>(List.of("up", "down", "left", "right"));

            Coord head = gameState.getYou().getHead();
            int boardWidth = gameState.getBoard().getWidth();
            int boardHeight = gameState.getBoard().getHeight();

            // Remove moves that would go outside the board
            if (head.getX() == 0) possibleMoves.remove("left");
            if (head.getX() == boardWidth - 1) possibleMoves.remove("right");
            if (head.getY() == 0) possibleMoves.remove("down");
            if (head.getY() == boardHeight - 1) possibleMoves.remove("up");

            // Filter unsafe moves (collisions)
            List<String> safeMoves = SnakeUtils.getSafeMoves(head, gameState, possibleMoves);

            // If no safe moves left, fallback to any possible move
            if (safeMoves.isEmpty()) {
                safeMoves = possibleMoves;
            }

            // Choose randomly among safe moves
            String chosenMove = safeMoves.get(new Random().nextInt(safeMoves.size()));

            return Response.ok(new SnakeMoveDTO(chosenMove)).build();
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
