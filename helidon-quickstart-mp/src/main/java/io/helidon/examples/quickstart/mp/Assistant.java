package io.helidon.examples.quickstart.mp;

import io.helidon.integrations.langchain4j.Ai;

@Ai.Service
@Ai.ChatMemoryWindow(10)  // 10 messages retained in memory
public interface Assistant {
    String chat(String message);
}
