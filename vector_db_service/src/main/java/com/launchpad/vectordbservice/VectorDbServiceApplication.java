package com.launchpad.vectordbservice;

import com.launchpad.vectordbservice.service.DocumentService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VectorDbServiceApplication {

    @Autowired
    DocumentService documentService;

    public static void main(String[] args) {
        SpringApplication.run(VectorDbServiceApplication.class, args);
    }

   // @PostConstruct
    public void prepareRAGData(){
        documentService.uploadEmbeddings();
    }

    @PreDestroy
    public void deleteRAGData(){
    }

}
