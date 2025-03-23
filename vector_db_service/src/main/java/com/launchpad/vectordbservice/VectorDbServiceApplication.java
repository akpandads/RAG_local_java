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
public class VectorDbServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VectorDbServiceApplication.class, args);
    }

}
