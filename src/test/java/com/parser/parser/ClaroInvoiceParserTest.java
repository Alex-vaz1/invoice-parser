package com.parser.parser;

import com.parser.exception.ParserException;
import com.parser.model.DatosFactura;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClaroInvoiceParserTest {

    private final ClaroInvoiceParser parser = new ClaroInvoiceParser();

    @Test
    void parsear_facturaValida_retornaDatos() {
        String textoFactura = """
                ADENDA
                RESUMEN DE LA CUENTA: 119232155 AL 01/03/2026
                01/02/2026 Saldo Anterior 497,26
                10/02/2026 Recibo Banco A 26347191 -497,26
                01/03/2026 e-Ticket C 6540830 494,40
                TOTAL A PAGAR $ 494,40
                """;

        DatosFactura resultado = parser.parsear(textoFactura);

        assertEquals("119232155", resultado.cuenta());
        assertEquals("494,40", resultado.monto());
        assertEquals("UYU", resultado.moneda());
    }

    @Test
    void parsear_sinCuenta_lanzaExcepcion() {
        String textoSinCuenta = "Este texto no tiene datos de factura";

        ParserException ex = assertThrows(
                ParserException.class,
                () -> parser.parsear(textoSinCuenta)
        );
        assertTrue(ex.getMessage().contains("cuenta"));
    }

    @Test
    void parsear_sinMonto_lanzaExcepcion() {
        String textoSinMonto = "RESUMEN DE LA CUENTA: 119232155 AL 01/03/2026\nSin monto";

        ParserException ex = assertThrows(
                ParserException.class,
                () -> parser.parsear(textoSinMonto)
        );
        assertTrue(ex.getMessage().contains("monto"));
    }
}
