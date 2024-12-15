package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

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
     * @param directives    The directives for the test case.
     * @return The created test case.
     * @throws IllegalArgumentException If the test type is unknown.
     */
    public static TestCase createTestCase(String testType, ClassRepresentation classToTest,
                                          MethodRepresentation methodToTest, List<Directive> directives) {
        switch (testType.toLowerCase()) {
            case "rainyday":
                return new RainyDayTestCase(classToTest, methodToTest, directives);
            case "happypath":
                return new HappyPathTestCase(classToTest, methodToTest, directives);
            default:
                throw new IllegalArgumentException("Unknown test type: " + testType);
        }
    }
}
