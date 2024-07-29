package codepilotunittest.factories;

import codepilotunittest.core.TestCase;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.testcasegeneration.ClassRepresentationTestCase;
import codepilotunittest.testcasegeneration.MethodRepresentationTestCase;
import codepilotunittest.testcasegeneration.ProjectRepresentationTestCase;

public class TestFactory {

    public static TestCaseGenerator getTestCaseGenerator(Object input) {
        if (input instanceof java.lang.Class<?>) {
            return new ClassRepresentationTestCase();  // Assuming `Class` is a concrete implementation
        } else if (input instanceof java.lang.reflect.Method) {
            return new MethodRepresentationTestCase();  // Assuming `Method` is another implementation
        } else if (input instanceof java.util.List) { // Assuming a list of classes represents a project
            return new ProjectRepresentationTestCase();  // Assuming `Project` is implemented
        }
        throw new IllegalArgumentException("No suitable TestCaseGenerator found for the given input");
    }

    public TestCase createTestCase() {
        return new TestCase();
    }
}
