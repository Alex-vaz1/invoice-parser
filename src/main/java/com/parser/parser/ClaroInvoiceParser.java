package com.parser.parser;

import com.parser.exception.InvoiceParseException;
import com.parser.model.InvoiceData;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClaroInvoiceParser {

    private static final Pattern ACCOUNT_PATTERN =
            Pattern.compile("RESUMEN DE LA CUENTA:\\s*(\\d+)");
    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("TOTAL A PAGAR\\s*\\$\\s*([\\d.,]+)");

    public InvoiceData parse(String text) {
        String cuenta = extractMatch(text, ACCOUNT_PATTERN, "cuenta");
        String monto = extractMatch(text, AMOUNT_PATTERN, "monto");
        return new InvoiceData(cuenta, monto, "UYU");
    }

    private String extractMatch(String text, Pattern pattern, String fieldName) {
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) {
            throw new InvoiceParseException(
                    "No se pudo extraer el campo '" + fieldName + "' del texto de la factura"
            );
        }
        return matcher.group(1).trim();
    }
}
