package com.parser.parser;

import com.parser.exception.ParserException;
import com.parser.model.DatosFactura;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClaroInvoiceParser {

    // regex armados a partir del texto que saca PDFBox de la factura
    private static final Pattern REGEX_CUENTA =
            Pattern.compile("RESUMEN DE LA CUENTA:\\s*(\\d+)");
    private static final Pattern REGEX_MONTO =
            Pattern.compile("TOTAL A PAGAR\\s*\\$\\s*([\\d.,]+)");

    public DatosFactura parsear(String texto) {
        String cuenta = buscarCampo(texto, REGEX_CUENTA, "cuenta");
        String monto = buscarCampo(texto, REGEX_MONTO, "monto");
        return new DatosFactura(cuenta, monto, "UYU");
    }

    private String buscarCampo(String texto, Pattern regex, String campo) {
        Matcher matcher = regex.matcher(texto);
        if (!matcher.find()) {
            throw new ParserException("No se encontro el campo '" + campo + "' en la factura");
        }
        return matcher.group(1).trim();
    }
}
