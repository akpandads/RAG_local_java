package com.launchpad.vectordbservice.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.requests.TranscriptParams;
import com.assemblyai.api.resources.transcripts.types.SpeechModel;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class AssemblyAIService {

    @Value("${assemblyai.api.key}")
    private String assemblyAIKey;

    public String voiceToText(File audioFile) throws IOException {
        AssemblyAI client = AssemblyAI.builder().apiKey(assemblyAIKey).build();
        Transcript transcript = client.transcripts().transcribe(audioFile);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            log.error("Error while transcribing", transcript.getError().get());
        }
        log.info("Transcription Done "+transcript.getText().get());
        return transcript.getText().get();
    }

}