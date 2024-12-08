package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

/**
 * Represents a generic test case.
 */
public interface TestCase {

    /**
     * Gets the name of the test case.
     *
     * @return the test name
     */
    String getTestName();

    /**
     * Gets the type of the test case.
     *
     * @return the test type
     */
    TestType getTestType();

    /**
     * Gets the class to be tested.
     *
     * @return the class representation
     */
    ClassRepresentation getClassToTest();

    /**
     * Gets the method to be tested.
     *
     * @return the method representation
     */
    MethodRepresentation getMethodToTest();

    /**
     * Gets the directives for this test case.
     *
     * @return a list of directives
     */
    List<Directive> getDirectives();
}
