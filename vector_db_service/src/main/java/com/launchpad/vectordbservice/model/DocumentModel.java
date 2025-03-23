package com.launchpad.vectordbservice.model;

import org.springframework.ai.document.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentModel {

    private String documentId;
    private List<Document> document;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<Document> getDocument() {
        if(document == null){
            document = new ArrayList<>();
        }
        return document;
    }
}
