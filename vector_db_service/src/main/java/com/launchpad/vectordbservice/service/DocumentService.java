package com.launchpad.vectordbservice.service;

import com.launchpad.vectordbservice.model.DocumentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    VectorStore vectorStore;

    @Autowired
    ResourceService resourceService;

    @Autowired
    WordFormatReader wordFormatReader;

    @Autowired
    PDFReader pdfReader;

    private List<String> documentIds = new ArrayList<>();

    public void uploadEmbeddings(){
        List<Document> documentToBeUploaded = new ArrayList<>();
        try {
            List<URL> fileList = resourceService.listFiles();
            fileList.forEach(file ->{
                DocumentModel documentModel = this.createDocumentFromInputFile(file);
                if(documentModel!=null){
                    log.info("Uploading tokens for document "+file.getFile());
                    documentToBeUploaded.addAll(documentModel.getDocument());
                    documentIds.add(documentModel.getDocumentId());
                }
            });
            vectorStore.add(documentToBeUploaded);

        } catch (IOException e) {
            log.error("Error while parsing files for embeddings");
            throw new RuntimeException(e);
        }
    }

    private DocumentModel createDocumentFromInputFile(URL url) {
        DocumentModel documentModel = null;
        List<Document> documents = null;
        String uuid = String.valueOf(UUID.randomUUID());
        try {
            if(url.getFile().endsWith(".doc")){
                 documents = wordFormatReader.loadText(url);
            }
            else if(url.getFile().endsWith(".pdf")){
                documents = pdfReader.getDocsFromPdfWithCatalog(url);
            }
        } catch (Exception e) {
            log.error("Error while reading file contents for ",url.getFile());
        }
        if(documents!= null){
            documentModel = new DocumentModel();
            documentModel.getDocument().addAll(documents);
            documentModel.setDocumentId(uuid);
        }

        return documentModel;
    }

}
