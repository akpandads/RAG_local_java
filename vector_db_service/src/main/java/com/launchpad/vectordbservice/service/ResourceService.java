package com.launchpad.vectordbservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ResourceService {
    private final ResourceLoader resourceLoader;

    public ResourceService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<URL> listFiles() throws IOException {
        List<URL> fileUrls = new ArrayList<>();
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:rag_data/*");

        for (Resource resource : resources) {
            try {
                URL fileUrl = resource.getURL();
                String fileName = resource.getFilename();
                if (fileName != null && (fileName.endsWith(".txt") || fileName.endsWith(".pdf") || fileName.endsWith(".doc"))) {
                    fileUrls.add(fileUrl);
                }
                log.info("Files added "+fileUrls.stream().peek(file -> log.info(file.getFile())).count());
            } catch (IOException e) {
                System.err.println("Cannot access file: " + resource.getFilename());
            }
        }
        return fileUrls;
    }
}

