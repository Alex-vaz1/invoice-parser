package com.parser.controller;

import com.parser.exception.InvoiceParseException;
import com.parser.model.InvoiceData;
import com.parser.service.InvoiceParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    private final InvoiceParserService parserService;

    public InvoiceController(InvoiceParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/parse")
    public ResponseEntity<?> parseInvoice(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El archivo está vacío"));
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El archivo debe ser un PDF"));
        }

        try {
            InvoiceData data = parserService.parseInvoice(file.getBytes());
            return ResponseEntity.ok(data);
        } catch (InvoiceParseException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error al leer el archivo PDF: " + e.getMessage()));
        }
    }
}
