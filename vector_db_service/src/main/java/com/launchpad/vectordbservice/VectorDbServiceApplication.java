package com.launchpad.vectordbservice;

import com.launchpad.vectordbservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VectorDbServiceApplication implements CommandLineRunner {

    @Autowired
    DocumentService documentService;

    public static void main(String[] args) {
        System.out.println("Staretd doc service");
        SpringApplication app = new SpringApplication(VectorDbServiceApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
        //documentService.testService();

        //SpringApplication.run(VectorDbServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        documentService.testService();
    }
}
