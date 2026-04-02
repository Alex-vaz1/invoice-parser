# Claro Invoice Parser API

API REST que parsea facturas PDF de Claro y extrae los datos de pago (numero de cuenta y monto).

## Requisitos

- Java 17
- Maven 3.8+

## Compilar y testear

```bash
mvn clean test
```

## Ejecutar

```bash
mvn spring-boot:run
```

La API estara disponible en `http://localhost:8080`.

## Uso

```bash
curl -F "file=@factura-claro.pdf" http://localhost:8080/api/v1/invoice/parse
```

Respuesta:
```json
{
  "cuenta": "119232155",
  "monto": "494,40",
  "moneda": "UYU"
}
```

## Stack

- Java 17
- Spring Boot 3.2
- Apache PDFBox 3.0 (extraccion de texto de PDFs)
- Regex (parsing de datos)
- JUnit 5 (tests)

## Arquitectura

```
POST PDF --> Controller --> Service --> PdfTextExtractor (PDFBox) --> texto plano
                                    --> ClaroInvoiceParser (regex) --> InvoiceData
         <-- JSON response <----------------------------------------------/
```

- **InvoiceController**: recibe el PDF, valida, retorna JSON
- **InvoiceParserService**: orquesta extraccion + parsing
- **PdfTextExtractor**: extrae texto del PDF con PDFBox
- **ClaroInvoiceParser**: aplica regex para encontrar cuenta y monto
- **InvoiceData**: record con los datos extraidos (cuenta, monto, moneda)
