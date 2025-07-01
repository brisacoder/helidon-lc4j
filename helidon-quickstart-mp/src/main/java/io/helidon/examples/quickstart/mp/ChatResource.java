package io.helidon.examples.quickstart.mp;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chat")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {

    /** ------------------------------------------------------
     * Build one model instance using the key from application.yaml
     * ------------------------------------------------------ */
    private static final OpenAiChatModel MODEL;

    static {
        // global MP-Config instance (Helidon wires this automatically)
        Config config = ConfigProvider.getConfig();

        // <=== property path exactly as it appears in application.yaml
        String apiKey = config.getValue(
                "langchain4j.open-ai.chat-model.api-key", String.class);

        String modelName = config.getOptionalValue(
                "langchain4j.open-ai.chat-model.model-name", String.class)
                                 .orElse("gpt-4o-mini");

        MODEL = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

    /* ---------- DTOs ---------- */
    public static final class ChatRequestDto { public String message; }
    public static final class ChatResponseDto {
        public String reply; ChatResponseDto(String r) { this.reply = r; }
    }

    /* ---------- POST /chat ---------- */
    @POST
    public ChatResponseDto chat(ChatRequestDto in) {
        ChatRequest req = ChatRequest.builder()
                                     .messages(UserMessage.from(in.message))
                                     .build();
        ChatResponse res = MODEL.chat(req);
        return new ChatResponseDto(res.aiMessage().text());
    }
}
