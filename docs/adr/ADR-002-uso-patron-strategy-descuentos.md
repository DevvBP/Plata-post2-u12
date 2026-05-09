# ADR-002: Uso del Patron Strategy para el Calculo de Descuentos

**Fecha:** 2026-05-08
**Estado:** Aceptado
**Autores:** Equipo de Arquitectura

---

## Contexto

El sistema aplica diferentes calculos de descuento dependiendo del tipo de cliente: VIP, Regular
y Nuevo. En una implementacion inicial, toda la logica de descuento residia en un unico metodo
con una cadena de if-else o un bloque switch. Esta aproximacion violo el principio Open/Closed
(OCP) de SOLID: cada vez que se agregaba un nuevo tipo de cliente, era necesario modificar el
metodo existente, arriesgando romper los calculos ya validados.

Se evaluaron dos alternativas:

1. Usar un bloque switch/case en la clase de servicio que procesa el pedido.
2. Extraer cada logica de descuento a una clase independiente bajo una interfaz comun
   (Patron Strategy de GoF).

## Decision

Se adopta el **Patron Strategy** mediante la interfaz `DescuentoStrategy` y tres implementaciones
concretas: `VipDescuentoStrategy`, `RegularDescuentoStrategy` y `NuevoClienteDescuentoStrategy`.

```
DescuentoStrategy (interfaz)
    |-- VipDescuentoStrategy
    |-- RegularDescuentoStrategy
    |-- NuevoClienteDescuentoStrategy
```

La seleccion de estrategia se realiza en `ProcesamientoPedidoFacade` mediante un switch de
expresion, lo que concentra el punto de decision en un lugar identificable y mantiene cada
algoritmo de descuento en su propia clase.

Para agregar un nuevo tipo de cliente (por ejemplo, "CORPORATIVO") basta con:
1. Crear una nueva clase que implemente `DescuentoStrategy`.
2. Registrarla como bean de Spring con `@Component`.
3. Agregar el caso en `resolverEstrategia()` de la fachada.

No se modifica ninguna clase existente, cumpliendo OCP.

## Consecuencias

**Positivas:**
- Cumplimiento del principio Open/Closed: nuevas estrategias no rompen las existentes.
- Cada estrategia es unitariamente testeable de forma independiente.
- La interfaz `DescuentoStrategy` actua como contrato verificable por ArchUnit (ver ADR-003).

**Negativas / Compromisos:**
- Aumenta el numero de clases en comparacion con un bloque switch en un solo metodo.
- Requiere que el equipo conozca el patron para mantener la coherencia al agregar estrategias.

---

*Documento generado en 2026. Unidad 12 - Gobernanza Arquitectonica.*
