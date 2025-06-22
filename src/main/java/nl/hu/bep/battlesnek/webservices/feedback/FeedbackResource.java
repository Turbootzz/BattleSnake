package nl.hu.bep.battlesnek.webservices.feedback;

import nl.hu.bep.battlesnek.model.Feedback;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/feedback")
public class FeedbackResource {
    private static final List<Feedback> feedbackList = new ArrayList<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitFeedback(FeedbackDTO dto) {
        Feedback feedback = new Feedback(dto.message, dto.username, LocalDateTime.now());
        feedbackList.add(feedback);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response getAllFeedback() {
        return Response.ok(feedbackList).build();
    }
}