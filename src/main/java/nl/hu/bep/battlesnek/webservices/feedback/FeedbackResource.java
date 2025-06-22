package nl.hu.bep.battlesnek.webservices.feedback;

import nl.hu.bep.battlesnek.model.Feedback;
import nl.hu.bep.battlesnek.persistence.FilePersistenceManager;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.List;

@Path("/feedback")
public class FeedbackResource {
    FilePersistenceManager persistence = FilePersistenceManager.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response submitFeedback(FeedbackDTO dto, @Context SecurityContext sc) {
        String username = sc.getUserPrincipal() != null ? sc.getUserPrincipal().getName() : "anonymous";
        Feedback feedback = new Feedback(dto.message, username, LocalDateTime.now().toString());
        persistence.addFeedback(feedback);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response getAllFeedback() {
        List<FeedbackDTO> dtos = persistence.getFeedbackList().stream()
                .map(f -> new FeedbackDTO(f.getMessage(), f.getUsername(), f.getSubmittedAt().toString()))
                .toList();
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response getFeedbackById(@PathParam("id") int id) {
        List<Feedback> allFeedback = persistence.getFeedbackList();
        if (id < 0 || id >= allFeedback.size()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Feedback ID not found").build();
        }
        Feedback feedback = allFeedback.get(id);
        FeedbackDTO dto = new FeedbackDTO(
                feedback.getMessage(),
                feedback.getUsername(),
                feedback.getSubmittedAt().toString()
        );
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteFeedbackById(@PathParam("id") int id) {
        List<Feedback> allFeedback = persistence.getFeedbackList();
        if (id < 0 || id >= allFeedback.size()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (allFeedback.remove(id) != null) {
            persistence.saveData();
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}