package edu.tlu.mcp_document.controller;

import edu.tlu.mcp_document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doc")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    List<Document> getDocument(@RequestParam String prompt) {
        return documentService.getDocument(prompt);
    }
}
