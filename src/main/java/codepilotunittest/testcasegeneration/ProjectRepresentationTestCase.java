package com.example.codepilotunittest.testcasegeneration;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import java.util.List;

public class ProjectRepresentationTestCase implements TestCaseGenerator {
    private List<java.lang.Class<?>> classes;  // Assuming a list of Class objects are part of the project

    public ProjectRepresentationTestCase(List<java.lang.Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public TestCases generateTestCases(Object input, List<Directive> directives) {
        TestCases projectTestCases = new TestCases();
        ClassRepresentation classRepresentationTestCaseGenerator = new ClassRepresentation();
        for (java.lang.Class<?> clazz : classes) {
            projectTestCases.merge(classRepresentationTestCaseGenerator.generateTestCases(clazz, directives));
        }
        return projectTestCases;
    }
}
