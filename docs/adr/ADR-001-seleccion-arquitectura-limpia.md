# ADR-001: Seleccion de Arquitectura Limpia para el Sistema de Pedidos

**Fecha:** 2026-05-08
**Estado:** Aceptado
**Autores:** Equipo de Arquitectura

---

## Contexto

El Sistema de Pedidos gestiona pedidos de distintos tipos (estandar, express, programado), aplica
reglas de descuento variables segun el perfil del cliente, coordina pagos e inventario, y notifica
a los interesados mediante eventos. Sin una separacion clara de responsabilidades, cualquier cambio
en la tecnologia de persistencia o de mensajeria arrastraria modificaciones al nucleo de negocio,
incrementando el riesgo de regresiones y aumentando el costo de mantenimiento.

Se evaluo una arquitectura en capas planas (traditional layered), una arquitectura hexagonal
(Ports & Adapters) y Arquitectura Limpia (Clean Architecture). El equipo necesitaba una opcion
que:

- Aislara completamente las reglas de negocio de los detalles tecnologicos.
- Facilitara las pruebas unitarias del dominio sin levantar el contexto de Spring.
- Pudiera aplicarse sin introducir frameworks adicionales al stack actual (Spring Boot 3.x, Java 17).

## Decision

Se adopta **Arquitectura Limpia** representada mediante circulos concentricos:

```
[domain]  <--  [facade/service]  <--  [controller]
                     ^
               [infrastructure]
```

La regla de dependencia fundamental es: **los circulos interiores no conocen a los exteriores**.
El paquete `domain` no importa ninguna clase de `infrastructure` ni de `controller`.
El paquete `facade` orquesta el flujo de negocio coordinando dominio e infraestructura.
El paquete `controller` solo conoce la fachada.

Esta decision se verifica de forma automatizada mediante ArchUnit (ver ADR-003).

## Consecuencias

**Positivas:**
- El dominio es completamente testeable en aislamiento sin necesidad del contexto de Spring.
- Un cambio de base de datos, sistema de mensajeria o proveedor de pagos no toca el dominio.
- La regla de dependencia de capas es verificable de forma estatica en cada build.

**Negativas / Compromisos:**
- Requiere disciplina para no saltarse la jerarquia de capas con atajos.
- Introduce mas paquetes y clases comparado con una arquitectura monolitica plana.
- Los desarrolladores nuevos deben familiarizarse con el modelo de circulos antes de contribuir.

---

*Documento generado en 2026. Unidad 12 - Gobernanza Arquitectonica.*
