package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

/**
 * Represents a test case for the "happy path" scenario.
 */
public class HappyPathTestCase implements TestCase {

    private final String testName;
    private final TestType testType;
    private final ClassRepresentation classToTest;
    private final MethodRepresentation methodToTest;
    private final Directive directive;

    public HappyPathTestCase(ClassRepresentation classToTest, MethodRepresentation methodToTest, Directive directive) {
        this.testName = methodToTest.getMethodName() + "_happyPath";
        this.testType = TestType.HAPPY_PATH;
        this.classToTest = classToTest;
        this.methodToTest = methodToTest;
        this.directive = directive;
    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Override
    public TestType getTestType() {
        return testType;
    }

    @Override
    public ClassRepresentation getClassToTest() {
        return classToTest;
    }

    @Override
    public MethodRepresentation getMethodToTest() {
        return methodToTest;
    }

    @Override
    public Directive getDirective() {
        return directive;
    }

    @Override
    public String toString() {
        return "HappyPathTestCase{" +
                "testName='" + testName + '\'' +
                ", testType=" + testType +
                ", classToTest=" + classToTest.getClassName() +
                ", methodToTest=" + methodToTest.getMethodName() +
                ", directives=" + directive +
                '}';
    }
}
