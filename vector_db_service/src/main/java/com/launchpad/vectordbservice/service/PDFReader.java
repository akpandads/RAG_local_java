package com.launchpad.vectordbservice.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
public class PDFReader {

    List<Document> getDocsFromPdfWithCatalog(URL url) {

        ParagraphPdfDocumentReader pdfReader = new ParagraphPdfDocumentReader(url.toString(),
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        return pdfReader.read();
    }
}
