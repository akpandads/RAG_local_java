package com.launchpad.vectordbservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.*;


@Component
@Slf4j
public class AudioRecorder {

    // Define the audio format (e.g., 44.1kHz, 16-bit, mono)
    private static final AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            44100, // sample rate (44.1 kHz)
            16,    // sample size in bits
            1,     // channels (1 = mono, 2 = stereo)
            2,     // frame size (2 bytes for 16-bit)
            44100, // frame rate (samples per second)
            false  // big endian
    );

    // Method to record audio for a fixed duration (e.g., 7 seconds)
    public void recordAudioForDuration(File outputFile, int durationInSeconds) throws LineUnavailableException, IOException {
        // Get the target data line (microphone)
        log.info("File name :",outputFile.getName());
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            log.error("Microphone is not supported");
            return;
        }

        // Open the target data line (microphone)
        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        // Prepare a byte array to store the audio data
        byte[] buffer = new byte[4096];

        // Create a file output stream to save the audio to a WAV file
        AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

        // Create an AudioInputStream to wrap the captured audio
        AudioInputStream audioStream = new AudioInputStream(microphone);

        // Start the recording in a separate thread
        Thread recordingThread = new Thread(() -> {
            try {
                System.out.println("Recording... Duration: " + durationInSeconds + " seconds.");
                // Write the recorded audio to the file
                AudioSystem.write(audioStream, fileType, outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start the recording thread
        recordingThread.start();

        // Record for the specified duration
        try {
            Thread.sleep(durationInSeconds * 1000); // Sleep for the specified duration (in milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the recording after the specified duration
        stopRecording(microphone);

        // Ensure the audio file is saved properly after stopping
        log.info("Recording stopped and saved to " + outputFile.getAbsolutePath());
    }

    // Method to stop the recording cleanly
    private static void stopRecording(TargetDataLine microphone) {
        microphone.stop();
        microphone.close();
    }
}
