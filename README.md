# Claro Invoice Parser

Parsea facturas PDF de Claro y extrae el numero de cuenta y el monto a pagar.

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

## Uso

```bash
curl -F "file=@factura-claro.pdf" http://localhost:8080/api/factura/parsear
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
- Apache PDFBox 3.0
- JUnit 5
