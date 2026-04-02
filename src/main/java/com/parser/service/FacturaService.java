package com.parser.service;

import com.parser.model.DatosFactura;
import com.parser.parser.ClaroInvoiceParser;
import com.parser.parser.PdfTextExtractor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FacturaService {

    private final PdfTextExtractor extractor;
    private final ClaroInvoiceParser parser;

    public FacturaService(PdfTextExtractor extractor, ClaroInvoiceParser parser) {
        this.extractor = extractor;
        this.parser = parser;
    }

    public DatosFactura parsearFactura(byte[] pdfBytes) throws IOException {
        String texto = extractor.extractText(pdfBytes);
        return parser.parsear(texto);
    }
}
