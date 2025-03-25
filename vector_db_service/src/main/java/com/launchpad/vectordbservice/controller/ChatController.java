package com.launchpad.vectordbservice.controller;

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

@Slf4j
@RestController
public class ChatController {

    private final OllamaChatModel chatModel;
    private final VectorStore vectorStore;

    @Autowired
    public ChatController(OllamaChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/ai/generate")
    public ChatResponse generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        ChatResponse response = ChatClient.builder(chatModel).build().prompt().advisors(new QuestionAnswerAdvisor((vectorStore))).user(message).call().chatResponse();
        return response;

    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    public ChatResponse generateIntent(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        log.info("Introducing intent");
        String systemMessage =" You are a agent to assist scrum masters and developers in managing their tickets" +
                "classify the intention of instruction and the action required." +
                "Response should give following fields" +
                "1. intent --- indicating the intent of instruction.it can be create, modify or delete only based on description" +
                "2. action --- 4 to 5 words to describe the title of ticket" +
                "3. description --- exact extracted descirption that will be put in the ticket"+
                "4. status --- new for new ticket creation, else intended status" +
                "5. ticketNumber --- NA for new tickets, else as provided in input";
        ChatResponse response = ChatClient.builder(chatModel).build().prompt().system(systemMessage)
                .user(message).call().chatResponse();
        log.info("LLM End");
        return response;

    }



}