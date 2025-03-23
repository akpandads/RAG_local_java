package com.launchpad.vectordbservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    VectorStore vectorStore;

    public void testService(){
        List<Document> documents = List.of(
                new Document("Triangle is not a shapr", Map.of("meta1", "meta1")),
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Qdrant
        vectorStore.add(documents);

// Retrieve documents similar to a query
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query("Lurks").topK(1).build());
        results.stream().forEach(
                result -> System.out.println(result.getFormattedContent()+","+result.getScore())
        );
        log.info("End of vector");
    }
}
