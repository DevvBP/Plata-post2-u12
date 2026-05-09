package com.universidad.patrones.config;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Validaciones automatizadas de reglas arquitectonicas mediante ArchUnit.
 * Garantiza el cumplimiento de los principios de Arquitectura Limpia
 * y los acuerdos registrados en los ADRs del proyecto.
 *
 * ADR-001: Arquitectura en capas con dominio aislado.
 * ADR-002: Patron Strategy para calculos de descuento.
 * ADR-003: Gobernanza automatizada con ArchUnit.
 */
class ReglasArquitecturaTest {

    private static final String PAQUETE_BASE = "com.universidad.patrones";

    private static JavaClasses clases;

    @BeforeAll
    static void cargarClases() {
        clases = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(PAQUETE_BASE);
    }

    // -----------------------------------------------------------------
    // REGLA 1: Dependencia de capas
    // El dominio es el nucleo y no puede conocer capas externas.
    // Referencia: ADR-001-seleccion-arquitectura-limpia
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Regla 1 - El paquete domain no debe depender de infrastructure ni de controller")
    void dominioNoDependeDeInfrastructureNiController() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..infrastructure..", "..controller..");

        regla.check(clases);
    }

    // -----------------------------------------------------------------
    // REGLA 2: Nomenclatura de servicios
    // Las clases que actuan como servicios de aplicacion deben terminar
    // en "Service" o en "ServiceImpl" para garantizar coherencia.
    // Referencia: ADR-002 / convencion de nomenclatura
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Regla 2 - Las clases en infrastructure que son @Service deben terminar en Service")
    void serviciosDeInfrastructureDebenTerminarEnService() {
        ArchRule regla = classes()
                .that().resideInAPackage("..infrastructure..")
                .and().areAnnotatedWith(org.springframework.stereotype.Service.class)
                .should().haveSimpleNameEndingWith("Service")
                        .orShould().haveSimpleNameEndingWith("ServiceImpl");

        regla.check(clases);
    }

    // -----------------------------------------------------------------
    // REGLA 3: Pureza del dominio
    // Las clases en el paquete domain no deben llevar anotaciones de
    // Spring Boot propias de capas de infraestructura (@Service,
    // @RestController, @Repository, @Controller).
    // Se permite @Component en factory/strategy para inyeccion DI.
    // Referencia: ADR-001-seleccion-arquitectura-limpia
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Regla 3 - Las clases de dominio no deben tener anotaciones de controlador o repositorio")
    void dominioNoPuedeUsarAnotacionesDeControladorORepositorio() {
        ArchRule regla = noClasses()
                .that().resideInAPackage("..domain..")
                .should().beAnnotatedWith(org.springframework.stereotype.Controller.class)
                        .orShould().beAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
                        .orShould().beAnnotatedWith(org.springframework.stereotype.Repository.class);

        regla.check(clases);
    }

    // -----------------------------------------------------------------
    // REGLA 4 (bonus): Nomenclatura de Facade
    // Las clases en la capa facade deben terminar en "Facade".
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Regla 4 - Las clases en la capa facade deben terminar en Facade")
    void clasesEnFacadeDebenTerminarEnFacade() {
        ArchRule regla = classes()
                .that().resideInAPackage("..facade..")
                .should().haveSimpleNameEndingWith("Facade");

        regla.check(clases);
    }

    // -----------------------------------------------------------------
    // REGLA 5 (bonus): Implementacion de contrato Strategy
    // Todas las estrategias concretas deben implementar DescuentoStrategy.
    // Referencia: ADR-002-uso-patron-strategy-descuentos
    // -----------------------------------------------------------------

    @Test
    @DisplayName("Regla 5 - Las clases concretas en domain.strategy deben implementar DescuentoStrategy")
    void estrategiasImplementanInterfazDescuento() {
        ArchRule regla = classes()
                .that().resideInAPackage("..domain.strategy..")
                .and().areNotInterfaces()
                .should().implement(
                        com.universidad.patrones.domain.strategy.DescuentoStrategy.class);

        regla.check(clases);
    }
}
