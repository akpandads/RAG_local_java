package com.launchpad.vectordbservice.controller;

import com.launchpad.vectordbservice.service.SpectacleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@Slf4j
public class ChatController {

    private final OllamaChatModel chatModel;
    private final VectorStore vectorStore;
    private final SpectacleService spectacleService;

    @Autowired
    public ChatController(OllamaChatModel chatModel, VectorStore vectorStore,SpectacleService spectacleService) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
        this.spectacleService = spectacleService;
    }

    @GetMapping("/ai/generate")
    public ChatResponse generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        ChatResponse response = ChatClient.builder(chatModel).build().prompt().advisors(new QuestionAnswerAdvisor((vectorStore))).user(message).call().chatResponse();
        return response;

    }

    @GetMapping("/ai/generate-with-tool-calling")
    public ChatResponse generateWithToolCalling(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        ChatResponse debugResponse = ChatClient.builder(chatModel).build().prompt()
                .user(message)
        .system("You are AI agent which will extract the values for leftEyePower, rightEyePower and bifocal(boolean) from the message. Default values of false and 0." +
                "Just the 3 fields. nignore any additional information")
                .call().chatResponse();
        log.info("debug response "+debugResponse);

        ChatResponse response = ChatClient.builder(chatModel).build().prompt()
                .user(message).system("You are AI agent which will extract the values for leftEyePower, rightEyePower and bifocal(boolean) from the message. Default values of false and 0." +
                        "Just the 3 fields and calculate the price").
        tools(spectacleService)
                .call().chatResponse();
        return response;

    }
    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }



}