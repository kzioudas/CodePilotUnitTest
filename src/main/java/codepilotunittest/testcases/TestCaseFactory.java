package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;
import java.util.Map;

/**
 * Factory class for creating test case instances.
 */
public class TestCaseFactory {

    /**
     * Creates a new test case based on the specified type.
     *
     * @param testType      The type of the test case.
     * @param classToTest   The class to be tested.
     * @param methodToTest  The method to be tested.
     * @param directive    The directives for the test case.
     * @return The created test case.
     * @throws IllegalArgumentException If the test type is unknown.
     */
    public static TestCase createTestCase(String testType, ClassRepresentation classToTest,
                                          MethodRepresentation methodToTest, Directive directive) {
        switch (testType.toLowerCase()) {
            case "rainyday":
                return new RainyDayTestCase(classToTest, methodToTest, directive);
            case "happypath":
                return new HappyPathTestCase(classToTest, methodToTest, directive);
            default:
                throw new IllegalArgumentException("Unknown test type: " + testType);
        }
    }
}
