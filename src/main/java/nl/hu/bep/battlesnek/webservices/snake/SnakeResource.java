package nl.hu.bep.battlesnek.webservices.snake;

import nl.hu.bep.battlesnek.model.Coord;
import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.model.GameState;
import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;
import nl.hu.bep.battlesnek.utils.SnakeUtils;
import nl.hu.bep.battlesnek.webservices.appearance.AppearanceResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/snake")
public class SnakeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnake() {
        return Response.ok(AppearanceResource.getCurrentAppearance()).build();
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
            // Get previous moves
            String gameId = gameState.getGame().getId();
            FilePersistenceManager persistence = FilePersistenceManager.getInstance();
            GameRecord gameRecord = persistence.getPlayedGames().get(gameId);

            String lastMove = null;
            if (gameRecord != null && !gameRecord.getMoves().isEmpty()) {
                // pick last move from its history
                lastMove = gameRecord.getMoves().get(gameRecord.getMoves().size() - 1);
            }

            // change possible moves
            List<String> possibleMoves = new ArrayList<>(List.of("up", "down", "left", "right"));

            // prevent to collision into itself
            if (lastMove != null) {
                switch (lastMove) {
                    case "up" -> possibleMoves.remove("down");
                    case "down" -> possibleMoves.remove("up");
                    case "left" -> possibleMoves.remove("right");
                    case "right" -> possibleMoves.remove("left");
                }
            }

            Coord head = gameState.getYou().getHead();
            int boardWidth = gameState.getBoard().getWidth();
            int boardHeight = gameState.getBoard().getHeight();

            // Remove moves that would go into the wall
            if (head.getX() == 0) possibleMoves.remove("left");
            if (head.getX() == boardWidth - 1) possibleMoves.remove("right");
            if (head.getY() == 0) possibleMoves.remove("down");
            if (head.getY() == boardHeight - 1) possibleMoves.remove("up");

            // Filter unsafe moves (collision with snakes)
            List<String> safeMoves = SnakeUtils.getSafeMoves(head, gameState, possibleMoves);

            String chosenMove;

            // If no safe moves left, fallback to any possible move
            if (safeMoves.isEmpty()) {
                System.out.println("WARN: No safe moves detected! Moving " + possibleMoves.get(0) + " as a last resort.");
                chosenMove = possibleMoves.get(0);
            } else {
                // There is atleast one safe move, SnakeUtils decides
                chosenMove = SnakeUtils.getSmartMove(head, gameState, safeMoves);
            }

            // -- Persistency - Saves the moves from the game
            Map<String, GameRecord> playedGames = persistence.getPlayedGames();
            GameRecord currentGameRecord = playedGames.getOrDefault(gameId, new GameRecord(gameId));

            currentGameRecord.addMove(chosenMove);

            playedGames.put(gameId, currentGameRecord);
            persistence.setPlayedGames(playedGames);
            persistence.saveData();

            return Response.ok(new SnakeMoveDTO(chosenMove)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/end")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endGame(GameState gameState) {
        String gameId = gameState.getGame().getId();
        String gameName = gameState.getGame().getName();

        System.out.println("Game ended: " + gameName);

        FilePersistenceManager persistence = FilePersistenceManager.getInstance();
        Map<String, GameRecord> playedGames = persistence.getPlayedGames();

        GameRecord gameRecord = playedGames.getOrDefault(gameId, new GameRecord(gameId));
        gameRecord.setTotalTurns(gameState.getTurn());

        playedGames.put(gameId, gameRecord);

        persistence.setPlayedGames(playedGames);
        persistence.saveData();

        return Response.ok().build();
    }
}
