package io.github.bahaa.webgpu;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import io.github.bahaa.webgpu.api.ObjectBase;
import io.github.bahaa.webgpu.internal.InstanceImpl;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    private final JavaClasses projectClasses = new ClassFileImporter()
            .importPackages("io.github.bahaa.webgpu");

    private final JavaClasses internalClasses = new ClassFileImporter()
            .importPackages("io.github.bahaa.webgpu.internal");

    @Test
    void allInternalClassesAreNotPublic() {
        classes()
                .that()
                .implement(ObjectBase.class)
                .and()
                .areNotAssignableFrom(InstanceImpl.class)
                .should()
                .bePackagePrivate()
                .check(this.projectClasses);

        classes()
                .that()
                .areNotAssignableFrom(InstanceImpl.class)
                .should()
                .bePackagePrivate()
                .orShould()
                .bePrivate()
                .orShould()
                .beProtected()
                .check(this.internalClasses);
    }
}
