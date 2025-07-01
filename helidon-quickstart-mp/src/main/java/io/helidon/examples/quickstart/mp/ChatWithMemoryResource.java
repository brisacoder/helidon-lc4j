package io.helidon.examples.quickstart.mp;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chat-memory")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatWithMemoryResource {

    @Inject
    private Assistant assistant;  // Helidon injects the AI-backed implementation

    // DTO for request
    public static final class ChatRequestDto {
        public String message;
    }

    // DTO for response
    public static final class ChatResponseDto {
        public String reply;

        public ChatResponseDto(String reply) {
            this.reply = reply;
        }
    }

    @POST
    public ChatResponseDto chat(ChatRequestDto in) {
        String reply = assistant.chat(in.message);
        return new ChatResponseDto(reply);
    }
}
