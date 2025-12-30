package io.github.bahaa.webgpu;

import com.tngtech.archunit.core.domain.*;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import io.github.bahaa.webgpu.api.NativeObject;
import io.github.bahaa.webgpu.api.ObjectBase;
import io.github.bahaa.webgpu.internal.InstanceImpl;

import java.lang.foreign.MemorySegment;
import java.util.stream.Collectors;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "io.github.bahaa.webgpu",
        importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchitectureTest {

    private static final String CLEANER_BASE_CLASS = "io.github.bahaa.webgpu.internal.NativeObjectImpl$ObjectCleaner";

    private static ArchCondition<JavaClass> haveCleanerDefined(final JavaClasses allClasses) {
        return new ArchCondition<>("have a cleaner defined that calls the correct release function") {
            @Override
            public void check(final JavaClass item, final ConditionEvents events) {
                final var nestedClasses = allClasses.stream()
                        .filter(it -> it.getEnclosingClass().isPresent())
                        .filter(it -> it.getEnclosingClass().get().equals(item))
                        .filter(it -> !it.getEnclosingClass().get().isAssignableFrom(CLEANER_BASE_CLASS))
                        .collect(Collectors.toUnmodifiableSet());
                if (nestedClasses.isEmpty()) {
                    events.add(SimpleConditionEvent.violated(item,
                            "class: %s do not have a nested class that extends ObjectCleaner"
                                    .formatted(item.getName())));
                    return;
                }

                if (nestedClasses.size() != 1) {
                    events.add(SimpleConditionEvent.violated(item,
                            "class: %s define ObjectCleaner more than once"
                                    .formatted(item.getName())));
                    return;
                }

                nestedClasses.stream().findFirst().ifPresent(nestedClass -> {
                    if (item.getMethod("cleaner")
                            .getAccessesFromSelf()
                            .stream()
                            .map(JavaAccess::getTarget)
                            .map(AccessTarget::getOwner)
                            .noneMatch(it -> it.equals(nestedClass))) {
                        events.add(SimpleConditionEvent.violated(item,
                                "class: %s implements ObjectCleaner, but do not use it in cleaner() method"
                                        .formatted(item.getName())));
                    }

                    final var releaseMethodName = "wgpu%sRelease".formatted(item.getSimpleName()
                            .replaceAll("Impl$", ""));

                    final var wgpuMethod = nestedClass.getMethod("clean", MemorySegment.class)
                            .getMethodCallsFromSelf()
                            .stream()
                            .filter(it -> it.getTarget().getName().equals(releaseMethodName))
                            .findFirst();

                    if (wgpuMethod.isEmpty()) {
                        events.add(SimpleConditionEvent.violated(item,
                                "Class: %s cleaner doesn't call %s function".formatted(item.getName(),
                                        releaseMethodName)));
                    }
                });

                events.add(SimpleConditionEvent.satisfied(item,
                        "class: %s has cleaner is implemented".formatted(item.getName())));
            }
        };
    }

    private static ArchCondition<JavaClass> haveCorrectSetLabelImplementation() {
        return new ArchCondition<>("have a set label implementation that calls correct set label function") {
            @Override
            public void check(final JavaClass item, final ConditionEvents events) {
                final var setLabelMethodName = "wgpu%sSetLabel".formatted(item.getSimpleName()
                        .replaceAll("Impl$", ""));

                final var method = item.getMethod("label", String.class)
                        .getMethodCallsFromSelf()
                        .stream()
                        .filter(it -> it.getTarget().getName().equals(setLabelMethodName))
                        .findFirst();

                if (method.isEmpty()) {
                    events.add(SimpleConditionEvent.violated(item,
                            "Class: %s label() doesn't call %s function".formatted(item.getName(),
                                    setLabelMethodName)));
                }
            }
        };
    }

    @ArchTest
    void allInternalClassesAreNotPublic(final JavaClasses classes) {
        classes()
                .that()
                .implement(ObjectBase.class)
                .and()
                .areNotAssignableFrom(InstanceImpl.class)
                .should()
                .bePackagePrivate()
                .check(classes);

        classes()
                .that()
                .implement(ObjectBase.class)
                .and()
                .areNotAssignableFrom(InstanceImpl.class)
                .should()
                .bePackagePrivate()
                .orShould()
                .bePrivate()
                .orShould()
                .beProtected()
                .check(classes);
    }

    @ArchTest
    void allApiObjectsHaveCleaners(final JavaClasses classes) {
        classes()
                .that()
                .areAssignableFrom(CLEANER_BASE_CLASS)
                .should()
                .beNestedClasses()
                .check(classes);

        classes()
                .that()
                .implement(NativeObject.class)
                .and()
                .doNotHaveModifier(JavaModifier.ABSTRACT)
                .should(haveCleanerDefined(classes))
                .check(classes);
    }

    @ArchTest
    void allApiObjectsCallTheCorrectSetLabelMethod(final JavaClasses classes) {
        classes()
                .that()
                .implement(ObjectBase.class)
                .and()
                .doNotHaveModifier(JavaModifier.ABSTRACT)
                .should(haveCorrectSetLabelImplementation())
                .check(classes);
    }
}
