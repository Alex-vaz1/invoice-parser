package com.parser.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void parseInvoice_withValidPdf_returns200WithData() throws Exception {
        byte[] pdfBytes = getClass().getClassLoader()
                .getResourceAsStream("sample-invoice.pdf")
                .readAllBytes();

        MockMultipartFile file = new MockMultipartFile(
                "file", "factura.pdf", "application/pdf", pdfBytes);

        mockMvc.perform(multipart("/api/v1/invoice/parse").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuenta").value("119232155"))
                .andExpect(jsonPath("$.monto").value("494,40"))
                .andExpect(jsonPath("$.moneda").value("UYU"));
    }

    @Test
    void parseInvoice_withEmptyFile_returns400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.pdf", "application/pdf", new byte[0]);

        mockMvc.perform(multipart("/api/v1/invoice/parse").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty());
    }

    @Test
    void parseInvoice_withInvalidFile_returns400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "not-a-pdf.txt", "text/plain", "hello".getBytes());

        mockMvc.perform(multipart("/api/v1/invoice/parse").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty());
    }
}
