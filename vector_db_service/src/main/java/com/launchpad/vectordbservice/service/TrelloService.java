package com.launchpad.vectordbservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Service
public class TrelloService {
    @Value("${trello.api.key}")
    private String apiKey;

    @Value("${trello.api.token}")
    private String apiToken;

    @Value("${trello.board.id}")
    private String boardId;

    @Value("${trello.todo.id}")
    private String todoId;

    @Value("${trello.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public TrelloService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createCard(String cardName, String description) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("idList", todoId)
                .queryParam("name", cardName)
                .queryParam("desc", description)
                .toUriString();

        log.info(url);
        // Make a POST request to Trello API to create the card
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        return response.toString();
    }
}
