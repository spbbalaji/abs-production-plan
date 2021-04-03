package com.abs.production.plan;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.abs.production.plan");

        noClasses()
            .that()
            .resideInAnyPackage("com.abs.production.plan.service..")
            .or()
            .resideInAnyPackage("com.abs.production.plan.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.abs.production.plan.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
