package dev.dashaun.ai.ollama.llamatwochat;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
class ChatController {

    private final OllamaChatClient chatClient;

    @Autowired
    ChatController(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/llama2/generate")
    Map generate(@RequestParam(value = "message", defaultValue = "Tell me how to say thanks for attending my session and its time for questions") String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @GetMapping("/llama2/generateStream")
    Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }

}