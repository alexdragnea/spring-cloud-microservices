package net.dg.bookservice.architectural;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import net.dg.bookservice.constants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitecturalTests {

	private JavaClasses importedClasses;

	@BeforeEach
	public void setup() {
		importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages(TestConstants.BASE_PACKAGE);
	}

	/* Annotation Checks */

	@Test
	void fieldInjectionNotUseAutowiredAnnotation() {

		noFields().should().beAnnotatedWith(Autowired.class).check(importedClasses);
	}

	@Test
	void serviceClassesShouldHaveSpringServiceAnnotation() {
		classes().that().resideInAPackage(TestConstants.SERVICE_PACKAGE).and().areNotInterfaces().should()
				.beAnnotatedWith(Service.class).check(importedClasses);
	}

	@Test
	void controllerClassesShouldHaveSpringRestControllerAnnotation() {
		classes().that().resideInAPackage(TestConstants.CONTROLLER_PACKAGE).should()
				.beAnnotatedWith(RestController.class).check(importedClasses);
	}

	@Test
	void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
		classes().that().resideInAPackage(TestConstants.REPOSITORY_PACKAGE).should().beAnnotatedWith(Repository.class)
				.check(importedClasses);
	}

	@Test
	void clientInterfacesShouldHaveFeignClientAnnotation() {
		classes().that().resideInAPackage(TestConstants.CLIENT_PACKAGE).should().beAnnotatedWith(FeignClient.class)
				.check(importedClasses);
	}

	@Test
	void configClassesShouldHaveConfigurationSpringAnnotation() {
		classes().that().resideInAPackage(TestConstants.CONFIG_PACKAGE).should().beAnnotatedWith(FeignClient.class)
				.check(importedClasses);
	}

	/* Package Dependency Checks */

	@Test
	void serviceClassesShouldNotDependOnWebLayer() {

		noClasses().that().resideInAnyPackage(TestConstants.SERVICE_PACKAGE).should().dependOnClassesThat()
				.resideInAnyPackage(TestConstants.CONTROLLER_PACKAGE)
				.because("Services and repositories should not depend on web layer").check(importedClasses);
	}

	/* Class Dependency Checks */

	@Test
	void serviceClassesShouldOnlyBeAccessedByController() {
		classes().that().resideInAPackage(TestConstants.SERVICE_PACKAGE).should().onlyBeAccessed()
				.byAnyPackage(TestConstants.SERVICE_PACKAGE, TestConstants.CONTROLLER_PACKAGE).check(importedClasses);
	}

	@Test
	void repositoryClassesShouldOnlyBeAccessedByController() {
		classes().that().resideInAPackage(TestConstants.REPOSITORY_PACKAGE).should().onlyBeAccessed()
				.byAnyPackage(TestConstants.SERVICE_PACKAGE).check(importedClasses);
	}

	/* Naming convention */

	@Test
	void serviceClassesShouldBeNamedXServiceOrXComponentOrXServiceImpl() {
		classes().that().resideInAPackage(TestConstants.SERVICE_PACKAGE).should().haveSimpleNameEndingWith("Service")
				.orShould().haveSimpleNameEndingWith("ServiceImpl").orShould().haveSimpleNameEndingWith("Component")
				.check(importedClasses);
	}

	@Test
	void repositoryClassesShouldBeNamedXRepository() {
		classes().that().resideInAPackage(TestConstants.REPOSITORY_PACKAGE).should()
				.haveSimpleNameEndingWith("Repository").check(importedClasses);
	}

	@Test
	void controllerClassesShouldBeNamedXController() {
		classes().that().resideInAPackage(TestConstants.CONTROLLER_PACKAGE).should()
				.haveSimpleNameEndingWith("Controller").check(importedClasses);
	}

	/* Layer Dependency Rules Test */

	@Test
	void layeredArchitectureShouldBeRespected() {

		layeredArchitecture().layer(TestConstants.CONTROLLER_LAYER).definedBy(TestConstants.CONTROLLER_PACKAGE)
				.layer(TestConstants.SERVICE_LAYER).definedBy(TestConstants.SERVICE_PACKAGE)
				.layer(TestConstants.SERVICE_IMPL_LAYER).definedBy(TestConstants.SERVICE_IMPL_PACKAGE)
				.layer(TestConstants.REPOSITORY_LAYER).definedBy(TestConstants.REPOSITORY_PACKAGE)
				.whereLayer(TestConstants.CONTROLLER_LAYER).mayNotBeAccessedByAnyLayer()
				.whereLayer(TestConstants.SERVICE_LAYER).mayOnlyBeAccessedByLayers(TestConstants.SERVICE_LAYER,
						TestConstants.SERVICE_IMPL_LAYER, TestConstants.CONTROLLER_LAYER)
				.check(importedClasses);
	}

	@Test
	void serviceClassesMustResideInServicePackage() {
		classes().that().haveNameMatching(".*Service").should().resideInAPackage(TestConstants.SERVICE_PACKAGE)
				.check(importedClasses);
	}

	@Test
	void repositoryClassesMustResideInRepositoryPackage() {
		classes().that().haveNameMatching(".*Repository").should().resideInAPackage(TestConstants.REPOSITORY_PACKAGE)
				.check(importedClasses);
	}

	@Test
	void controllerClassesMustResideInServicePackage() {
		classes().that().haveNameMatching(".*Controller").should().resideInAPackage(TestConstants.CONTROLLER_PACKAGE)
				.check(importedClasses);
	}

}
