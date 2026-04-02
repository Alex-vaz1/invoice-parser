package com.parser.service;

import com.parser.model.InvoiceData;
import com.parser.parser.ClaroInvoiceParser;
import com.parser.parser.PdfTextExtractor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoiceParserService {

    private final PdfTextExtractor extractor;
    private final ClaroInvoiceParser parser;

    public InvoiceParserService(PdfTextExtractor extractor, ClaroInvoiceParser parser) {
        this.extractor = extractor;
        this.parser = parser;
    }

    public InvoiceData parseInvoice(byte[] pdfBytes) throws IOException {
        String text = extractor.extractText(pdfBytes);
        return parser.parse(text);
    }
}
