package com.example.codepilotunittest.testcasegeneration;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import java.util.List;

public class Project implements TestCaseGenerator {
    private List<java.lang.Class<?>> classes;  // Assuming a list of Class objects are part of the project

    public Project(List<java.lang.Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public TestCases generateTestCases(Object input, List<Directive> directives) {
        TestCases projectTestCases = new TestCases();
        Class classTestCaseGenerator = new Class();
        for (java.lang.Class<?> clazz : classes) {
            projectTestCases.merge(classTestCaseGenerator.generateTestCases(clazz, directives));
        }
        return projectTestCases;
    }
}
