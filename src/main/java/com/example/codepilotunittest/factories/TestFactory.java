package com.example.codepilotunittest.factories;

import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import com.example.codepilotunittest.testcasegeneration.ClassRepresentation;
import com.example.codepilotunittest.testcasegeneration.MethodRepresentation;
import com.example.codepilotunittest.testcasegeneration.ProjectRepresentation;

public class TestFactory {

    public static TestCaseGenerator getTestCaseGenerator(Object input) {
        if (input instanceof java.lang.Class<?>) {
            return new ClassRepresentation();  // Assuming `Class` is a concrete implementation
        } else if (input instanceof java.lang.reflect.Method) {
            return new MethodRepresentation();  // Assuming `Method` is another implementation
        } else if (input instanceof java.util.List) { // Assuming a list of classes represents a project
            return new ProjectRepresentation();  // Assuming `Project` is implemented
        }
        throw new IllegalArgumentException("No suitable TestCaseGenerator found for the given input");
    }
}
