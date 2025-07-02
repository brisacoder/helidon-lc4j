# Helidon + LangChain4j Basic Chatbot

This project demonstrates a simple chatbot using **Helidon** and **LangChain4j**, exposing two REST endpoints:

* **`/chat`** — Stateless, fire-and-forget chatbot interaction.
* **`/chat-memory`** — Chatbot with short-term memory (remembers the last 10 messages).

## Configuration

You must provide an OpenAI API key to use this chatbot.

Create an `application.yaml` file under:

```
helidon-quickstart-mp/src/main/resources/application.yaml
```

Example configuration:

```yaml
langchain4j:
  open-ai:
    chat-model:
      enabled: true
      api-key: "your-openai-key"
      # Or use environment variable fallback
      # api-key: "${OPENAI_API_KEY:demo}"
      model-name: "gpt-4.1"
      max-retries: 3
      temperature: 0.7
    memory:
      type: window
      max-messages: 10
    streaming-chat-model:
      enabled: false
```

**Note:** Replace `"your-openai-key"` with your actual OpenAI API key.


## Running the Application

```bash
mvn package
java -jar target/helidon-quickstart-mp.jar
```

The server starts on port `8080`.

## Example Usage

### Stateless Chat (`/chat`)

```bash
curl -X POST -H "Content-Type: application/json" -d '{ "message": "Hello, my name is Klaus." }' http://localhost:8080/chat

curl -X POST -H "Content-Type: application/json" -d '{ "message": "What is my name?" }' http://localhost:8080/chat
```

### Chat with Memory (`/chat-memory`)

```bash
curl -X POST -H "Content-Type: application/json" -d '{ "message": "Hello, my name is Klaus." }' http://localhost:8080/chat-memory

curl -X POST -H "Content-Type: application/json" -d '{ "message": "What is my name?" }' http://localhost:8080/chat-memory
```

The `/chat-memory` endpoint retains the last **10 messages** in memory per session.

## Video Demonstration

📽 [Watch the video demo](./helidon_plus_lc4j.mp4)

---

## Known Issues & Compatibility Notes

⚠ **LangChain4j Version Mismatch**

* Helidon integrates with `langchain4j` **version `0.36.2`**.
* The latest official `langchain4j` release is **`1.1.0`** — [LangChain4j GitHub](https://github.com/langchain4j/langchain4j).

### Implications:

* **Examples and tutorials for `langchain4j 1.1.0` do not work out-of-the-box with Helidon.**
* The new **dependency injection mechanisms** introduced by Helidon are not fully compatible with API changes in `langchain4j 1.1.0`.

If you're following tutorials or documentation for `langchain4j` 1.x, expect discrepancies when running within Helidon until Helidon updates its integration.

## Feedback and Contributions

Feel free to fork this project, experiment, and suggest improvements.

