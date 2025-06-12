package nl.hu.bep.battlesnek.webservices.appearance;

import nl.hu.bep.battlesnek.persistence.BattlesnakeData;

import nl.hu.bep.battlesnek.model.SnakeAppearance;
import nl.hu.bep.battlesnek.persistence.PersistenceManager;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppearance() {
        return Response.ok(getCurrentAppearance()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppearance(AppearanceDTO dto) {
        // Update saved appearance with new data
        getCurrentAppearance().setColor(dto.color);
        getCurrentAppearance().setHead(dto.head);
        getCurrentAppearance().setTail(dto.tail);
        PersistenceManager.saveDataToFile(getCurrentAppearance());

        return Response.ok().build();
    }

    public static void setAndPersistAppearance(SnakeAppearance newAppearance) {
        PersistenceManager.setAppearance(newAppearance);
        PersistenceManager.saveDataToFile(newAppearance);
    }

    public static void loadAppearanceAtStartup() {
        BattlesnakeData data = PersistenceManager.loadDataFromFile();
        if (data != null && data.appearance != null) {
            setAndPersistAppearance(data.appearance);
        }
    }
}
