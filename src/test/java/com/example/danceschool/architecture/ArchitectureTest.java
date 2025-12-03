package com.example.danceschool.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(
        packages = "com.example.danceschool",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ArchitectureTest {

    @ArchTest
    static final Architectures.LayeredArchitecture layeredArchitecture = Architectures.layeredArchitecture()
            .consideringAllDependencies()

            // Define layers
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..")
            .layer("Model").definedBy("..model..")
            .layer("DTO").definedBy("..dto..")

            // Allow rules
            .whereLayer("Controller").mayOnlyAccessLayers("Service", "DTO")
            .whereLayer("Service").mayOnlyAccessLayers("Repository", "Model", "DTO")
            .whereLayer("Repository").mayOnlyAccessLayers("Model")
            .whereLayer("DTO").mayNotAccessAnyLayer()
            .whereLayer("Model").mayNotAccessLayers("Controller", "Service", "Repository");

    @ArchTest
    static final com.tngtech.archunit.lang.ArchRule no_cycles =
            SlicesRuleDefinition.slices()
                    .matching("com.example.danceschool.(*)..")
                    .should().beFreeOfCycles();
}