# Taller Grupal 3: Lambdas y patrón Strategy

**Universidad Agraria del Ecuador · Carrera de Computación en Línea**
Programación 3 (Orientada a Objetos Avanzada) · Semana 3

**Grupo:** GRUPO 10
**Paralelo:** 4SC

## Integrantes y roles
| Integrante | Rol |
| --- | --- |
| Maria Korina Murillo Torres | Coordinadora, desarrolladora principal y revisora de calidad |

## Descripción

Calculadora de costos de producción agropecuaria en Java 21. Las fórmulas de cálculo son intercambiables en tiempo de ejecución mediante el patrón Strategy, con interfaces funcionales y lambdas sobre una jerarquía sellada de cultivos (banano, cacao y arroz).

## Cómo ejecutar

Requisitos: JDK 21 o superior y Maven.

    mvn -q compile exec:java    # ejecuta el programa de demostración
    mvn test                    # ejecuta las pruebas unitarias