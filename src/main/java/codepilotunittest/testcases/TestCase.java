package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

/**
 * Represents a test case with essential attributes such as test name, type,
 * class to test, method to test, and directives.
 */
public interface TestCase {

    /**
     * Gets the name of the test case.
     *
     * @return the name of the test case
     */
    String getTestName();

    /**
     * Gets the type of the test case, which indicates the nature of the test
     * (e.g., "rainyday", "happypath").
     *
     * @return the type of the test case
     */
    TestType getTestType();

    /**
     * Gets the fully qualified name of the class that will be tested.
     *
     * @return the class to be tested
     */
    ClassRepresentation getClassToTest();

    /**
     * Gets the method in the class that will be tested.
     *
     * @return the method to be tested
     */
    MethodRepresentation getMethodToTest();

    /**
     * Gets the list of directives that guide how the test should be performed.
     * These directives may vary depending on the test type.
     *
     * @return a list of directives for the test case
     */
    List<Directive> getDirectives();
}
