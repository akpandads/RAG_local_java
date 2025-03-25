package com.launchpad.vectordbservice.service;

import com.google.gson.Gson;
import com.launchpad.vectordbservice.model.IntentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TextToActionService {

    @Autowired
    private TrelloService trelloService;

    private IntentModel determineAction(ChatResponse response){
        String extractedInformation = response.getResults().get(0).getOutput().getText();
        Gson gson = new Gson();
        IntentModel intentModel = gson.fromJson(extractedInformation, IntentModel.class);
        return intentModel;
    }

    public void performAction(ChatResponse response){
        IntentModel intentModel = determineAction(response);
        switch (intentModel.getIntent()){
            case "create":
                trelloService.createCard(intentModel.getAction(),intentModel.getDescription());
            default:
                log.info("No suitable intent or action found");
        }
    }
}
