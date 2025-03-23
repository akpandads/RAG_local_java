package com.launchpad.vectordbservice.service;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;

@Component
public class WordFormatReader {

    List<Document> loadText(URL url) {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(url.toString());
        return tikaDocumentReader.read();
    }
}
