package nl.hu.bep.battlesnek.webservices.games;

import nl.hu.bep.battlesnek.model.GameRecord;
import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Set;

@Path("/games")
public class GamesResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameIds() {
        FilePersistenceManager persistence = FilePersistenceManager.getInstance();
        Set<String> gameIds = persistence.getPlayedGames().keySet();
        return Response.ok(gameIds).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameDetails(@PathParam("id") String gameId) {
        FilePersistenceManager persistence = FilePersistenceManager.getInstance();
        GameRecord game = persistence.getPlayedGames().get(gameId);
        if (game == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GameRecordDTO dto = new GameRecordDTO(
                game.getGameId(),
                game.getMoves(),
                game.getTotalTurns()
        );

        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteGame(@PathParam("id") String gameId) {
        FilePersistenceManager persistence = FilePersistenceManager.getInstance();
        Map<String, GameRecord> games = persistence.getPlayedGames();
        if (games.remove(gameId) != null) {
            persistence.saveData();
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}