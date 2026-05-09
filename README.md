# Sistema de Pedidos - Laboratorio de Gobernanza Arquitectonica

Unidad 12, Post-Contenido 2  
Fecha: 2026

---

## Descripcion del Proyecto

Sistema de pedidos construido con Spring Boot 3.x y Java 17 que implementa Arquitectura Limpia,
el patron Strategy para calculos de descuento, el patron Factory para creacion de pedidos y el
patron Observer para notificaciones de eventos. El proyecto incorpora validacion estatica de
reglas arquitectonicas mediante ArchUnit y un pipeline de integracion continua con GitHub Actions.

---

## Arquitectura del Sistema

El proyecto sigue el modelo de circulos concentricos de Arquitectura Limpia. La regla fundamental
es que los circulos interiores no conocen a los exteriores.

```
[domain]  <--  [facade]  <--  [controller]
                   ^
            [infrastructure]
```

| Capa           | Paquete                              | Responsabilidad                              |
|----------------|--------------------------------------|----------------------------------------------|
| domain         | com.universidad.patrones.domain      | Entidades, Factory, Strategy, Eventos        |
| facade         | com.universidad.patrones.facade      | Orquestacion del flujo de negocio            |
| infrastructure | com.universidad.patrones.infrastructure | Servicios tecnicos: Inventario, Pago, SMS, Email |
| controller     | com.universidad.patrones.controller  | Punto de entrada HTTP (REST)                 |

---

## Decisiones Arquitectonicas Registradas (ADRs)

| ID      | Titulo                                              | Estado   |
|---------|-----------------------------------------------------|----------|
| ADR-001 | Seleccion de Arquitectura Limpia                    | Aceptado |
| ADR-002 | Uso del Patron Strategy para Calculos de Descuento  | Aceptado |
| ADR-003 | Implementacion de ArchUnit para Gobernanza          | Aceptado |

Los documentos completos se encuentran en la carpeta `docs/adr/`.

---

## Reglas de Arquitectura (ArchUnit)

La clase `ReglasArquitecturaTest` en `src/test/java/.../config/` define y valida de forma
automatica las siguientes reglas en cada ejecucion del build:

| Regla | Descripcion                                                                          |
|-------|--------------------------------------------------------------------------------------|
| 1     | El paquete domain no puede depender de infrastructure ni de controller               |
| 2     | Los servicios de infrastructure deben terminar en "Service" o "ServiceImpl"          |
| 3     | Las clases de dominio no pueden tener anotaciones @Controller, @RestController o @Repository |
| 4     | Las clases en la capa facade deben terminar en "Facade"                              |
| 5     | Las estrategias concretas en domain.strategy deben implementar DescuentoStrategy     |

Si cualquiera de estas reglas se viola, el test falla con un mensaje descriptivo que identifica
la clase infractora y la regla incumplida.

---

## Evidencia de CI/CD

### Pipeline fallando (violacion intencional)

La imagen `docs/pipeline_rojo.png` muestra el pipeline de GitHub Actions en estado de error
luego de introducir intencionalmente una dependencia cruzada desde el dominio hacia el
controlador, violando la Regla 1 de ArchUnit.

### Pipeline exitoso (arquitectura corregida)

La imagen `docs/pipeline_verde.png` muestra el pipeline de GitHub Actions en estado exitoso
una vez eliminada la violacion, confirmando que todas las reglas arquitectonicas se cumplen.

---

## Como Ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/DevvBP/Plata-post2-u12.git
cd Plata-post2-u12/sistema-pedidos

# Compilar y ejecutar todos los tests (incluye ArchUnit)
mvn test

# Levantar la aplicacion
mvn spring-boot:run
```

---

## Endpoint Disponible

```
POST /api/pedidos/procesar
  ?tipoPedido=ESTANDAR|EXPRESS|PROGRAMADO
  &tipoCliente=VIP|NUEVO|REGULAR
  &cantidad=5
  &precio=100.0
```

---

## Tecnologias

| Tecnologia    | Version |
|---------------|---------|
| Java          | 17      |
| Spring Boot   | 3.2.5   |
| ArchUnit      | 1.3.0   |
| JaCoCo        | 0.8.11  |
| JUnit 5       | 5.x     |
| GitHub Actions| -       |

---

*Sistema de Pedidos - Gobernanza Arquitectonica con ArchUnit y ADR. 2026*
