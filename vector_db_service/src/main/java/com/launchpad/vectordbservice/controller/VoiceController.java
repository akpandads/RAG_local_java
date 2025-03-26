package com.launchpad.vectordbservice.controller;


import com.launchpad.vectordbservice.service.AssemblyAIService;
import com.launchpad.vectordbservice.service.AudioRecorder;
import com.launchpad.vectordbservice.service.DocumentService;
import com.launchpad.vectordbservice.service.TextToActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
public class VoiceController {

    @Autowired
    DocumentService documentService;

    @Autowired
    AudioRecorder audioRecorder;

    @Autowired
    AssemblyAIService assemblyAIService;

    @Autowired
    ChatController chatController;

    @Autowired
    TextToActionService textToActionService;

    @Value("${audio.file.name}")
    private String audioFileName;


    @GetMapping("/ai/voice-command")
    public String processVoiceCommand(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        log.info("Received request, starting audio recording");

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

        return "Processed";

    }
}
