package com.parser.parser;

import java.io.InputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PdfTextExtractorTest {

    private final PdfTextExtractor extractor = new PdfTextExtractor();

    @Test
    void extractText_withValidPdf_returnsNonEmptyText() throws Exception {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("sample-invoice.pdf");
        assertNotNull(is, "sample-invoice.pdf not found in test resources");
        byte[] pdfBytes = is.readAllBytes();

        String text = extractor.extractText(pdfBytes);

        assertNotNull(text);
        assertFalse(text.isBlank());
    }

    @Test
    void extractText_withInvalidBytes_throwsException() {
        byte[] invalidBytes = "not a pdf".getBytes();

        assertThrows(Exception.class, () -> extractor.extractText(invalidBytes));
    }
}
