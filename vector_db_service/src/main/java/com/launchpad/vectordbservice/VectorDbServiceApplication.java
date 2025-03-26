package com.launchpad.vectordbservice;

import com.launchpad.vectordbservice.controller.ChatController;
import com.launchpad.vectordbservice.service.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@Slf4j
@SpringBootApplication
public class VectorDbServiceApplication{

    @Autowired
    DocumentService documentService;

    public static void main(String[] args) {
        SpringApplication.run(VectorDbServiceApplication.class, args);
    }


    @PostConstruct
    public void prepareRAGData(){
        documentService.uploadEmbeddings();
    }

    @PreDestroy
    public void deleteRAGData(){
    }

/*    @Override
    public void run(String... args) throws Exception {
        log.info("Started command "+audioFileName);
        File audioFile = new File(audioFileName);
        try {

            audioRecorder.recordAudioForDuration(audioFile, 10);
            String command = assemblyAIService.voiceToText(audioFile);
            ChatResponse response = chatController.generateIntent(command);
            log.info(response.toString());
            textToActionService.performAction(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("End Command");
    }*/
}
