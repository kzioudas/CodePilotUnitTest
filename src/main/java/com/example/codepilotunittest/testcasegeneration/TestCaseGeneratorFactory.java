package com.example.codepilotunittest.testcasegeneration;

import com.example.codepilotunittest.interfaces.TestCaseGenerator;

public class TestCaseGeneratorFactory {

    public static TestCaseGenerator getTestCaseGenerator(Object input) {
        if (input instanceof java.lang.Class<?>) {
            return new Class();  // Assuming `Class` is a concrete implementation
        } else if (input instanceof java.lang.reflect.Method) {
            return new Method();  // Assuming `Method` is another implementation
        } else if (input instanceof java.util.List) { // Assuming a list of classes represents a project
            return new Project();  // Assuming `Project` is implemented
        }
        throw new IllegalArgumentException("No suitable TestCaseGenerator found for the given input");
    }
}
