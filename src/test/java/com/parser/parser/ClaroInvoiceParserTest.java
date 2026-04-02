package com.parser.parser;

import com.parser.exception.InvoiceParseException;
import com.parser.model.InvoiceData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClaroInvoiceParserTest {

    private final ClaroInvoiceParser parser = new ClaroInvoiceParser();

    @Test
    void parse_withValidInvoiceText_returnsInvoiceData() {
        String invoiceText = """
                ADENDA
                RESUMEN DE LA CUENTA: 119232155 AL 01/03/2026
                01/02/2026 Saldo Anterior 497,26
                10/02/2026 Recibo Banco A 26347191 -497,26
                01/03/2026 e-Ticket C 6540830 494,40
                TOTAL A PAGAR $ 494,40
                """;

        InvoiceData result = parser.parse(invoiceText);

        assertEquals("119232155", result.cuenta());
        assertEquals("494,40", result.monto());
        assertEquals("UYU", result.moneda());
    }

    @Test
    void parse_withMissingAccount_throwsException() {
        String textWithoutAccount = "Este texto no tiene datos de factura";

        InvoiceParseException ex = assertThrows(
                InvoiceParseException.class,
                () -> parser.parse(textWithoutAccount)
        );
        assertTrue(ex.getMessage().contains("cuenta"));
    }

    @Test
    void parse_withMissingAmount_throwsException() {
        String textWithoutAmount = "RESUMEN DE LA CUENTA: 119232155 AL 01/03/2026\nSin monto";

        InvoiceParseException ex = assertThrows(
                InvoiceParseException.class,
                () -> parser.parse(textWithoutAmount)
        );
        assertTrue(ex.getMessage().contains("monto"));
    }
}
