# ADR-003: Implementacion de ArchUnit para Gobernanza Arquitectonica Automatizada

**Fecha:** 2026-05-08
**Estado:** Aceptado
**Autores:** Equipo de Arquitectura

---

## Contexto

Documentar reglas arquitectonicas en wikis o documentos de texto no es suficiente para garantizar
su cumplimiento. La experiencia del equipo muestra que, bajo presion de entrega, los desarrolladores
tienden a tomar atajos que violan las fronteras de capas: una clase de dominio que importa un
repositorio de infraestructura, un controlador que accede directamente al dominio sin pasar por
la fachada, o una estrategia de descuento que rompe su contrato de interfaz.

Estos atajos son silenciosos: el proyecto compila, las pruebas de negocio pasan, pero la
arquitectura se degrada lentamente (arquitectura erosionada). El deterioro solo se detecta en
revisiones manuales de codigo, que son costosas y poco frecuentes.

El equipo evaluo tres opciones para automatizar la verificacion:

1. Revisiones manuales de codigo en pull requests (bajo nivel de automatizacion).
2. Reglas de checkstyle o PMD (solo verifican estilo, no dependencias entre paquetes).
3. **ArchUnit**: libreria de pruebas para Java que permite expresar reglas de arquitectura
   como tests JUnit5 ejecutables en el pipeline de CI/CD.

## Decision

Se adopta **ArchUnit 1.3.0** integrado en la suite de pruebas JUnit5 del proyecto.

Las reglas se definen en la clase `ReglasArquitecturaTest` dentro del paquete
`com.universidad.patrones.config` de la carpeta de test. Cada regla tiene un nombre descriptivo
(`@DisplayName`) que sirve como documentacion ejecutable.

Las reglas implementadas cubren:
- Aislamiento del dominio respecto a infrastructure y controller.
- Nomenclatura de servicios en la capa de infraestructura (sufijo "Service" o "ServiceImpl").
- Pureza del dominio frente a anotaciones de controlador o repositorio.
- Convencion de nomenclatura para la capa facade (sufijo "Facade").
- Cumplimiento del contrato `DescuentoStrategy` por todas las estrategias concretas.

Estas reglas se ejecutan en cada push a la rama `main` mediante el pipeline de GitHub Actions
definido en `.github/workflows/arquitectura.yml`.

## Consecuencias

**Positivas:**
- La arquitectura se vuelve verificable: una violacion rompe el build de forma visible en CI/CD.
- Las reglas son codigo vivo: evolucionan junto con el proyecto y se versionan en Git.
- Sirven como documentacion ejecutable que todo desarrollador puede leer y ejecutar localmente.
- Reducen la deuda tecnica silenciosa derivada de atajos arquitectonicos.

**Negativas / Compromisos:**
- Agrega tiempo de compilacion y analisis en el pipeline (tipicamente menos de 10 segundos).
- Las reglas deben actualizarse cuando la estructura de paquetes cambia intencionalmente.
- Requiere que el equipo entienda los conceptos de ArchUnit para escribir nuevas reglas.

---

*Documento generado en 2026. Unidad 12 - Gobernanza Arquitectonica.*
