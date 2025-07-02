# Basic Chatbot

This basic chatbnot exposes two REST endpoints: `/chat` and `/chat-memory`

- `chat` implements a stateless, fire-and-forget chatbot
- `chat-memory` is more sophisticated and retains conversation memory (up to 10 messages)

## Configuration

Create an `application.yaml` file under `helidon-quickstart-mp\src\main\resources` as below. You need to provide an official OpenAI API key.

```yaml
langchain4j:
  open-ai:
    chat-model:
      enabled: true
      api-key: "your openai key"
      #api-key: "${OPENAI_API_KEY:demo}"
      model-name: "gpt-4.1"
      max-retries: 3
      temperature: 0.7 
    memory:
      type: window
      max-messages: 10
    streaming-chat-model:
      enabled: false
```

## Running Instructions

```bash
mvn package
java -jar target/helidon-quickstart-mp.jar
```

## Testing

```bash
curl -X POST -H "Content-Type: application/json" -d '{ "message": "Hello, my name is Klaus." }' http://localhost:8080/chat


curl -X POST -H "Content-Type: application/json" -d '{ "message": "What is my name?" }' http://localhost:8080/chat


curl -X POST -H "Content-Type: application/json" -d '{ "message": "Hello, my name is Klaus." }' http://localhost:8080/chat-memory


curl -X POST -H "Content-Type: application/json" -d '{ "message": "What is my name?" }' http://localhost:8080/chat-memory
```

## Video Demo

[Watch the video demo](./helidon_plus_lc4j.mp4)
