package codepilotunittest.factories;

import codepilotunittest.interfaces.TestCaseGenerator;

public class TestFactory {

    public static TestCaseGenerator getTestCaseGenerator(Object input) {
        if (input instanceof java.lang.Class<?>) {
            return new com.example.codepilotunittest.testcasegeneration.ClassRepresentationTestCase();  // Assuming `Class` is a concrete implementation
        } else if (input instanceof java.lang.reflect.Method) {
            return new com.example.codepilotunittest.testcasegeneration.MethodRepresentationTestCase();  // Assuming `Method` is another implementation
        } else if (input instanceof java.util.List) { // Assuming a list of classes represents a project
            return new com.example.codepilotunittest.testcasegeneration.ProjectRepresentationTestCase();  // Assuming `Project` is implemented
        }
        throw new IllegalArgumentException("No suitable TestCaseGenerator found for the given input");
    }
}
