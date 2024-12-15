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
    private final List<Directive> directives;

    public HappyPathTestCase(ClassRepresentation classToTest, MethodRepresentation methodToTest, List<Directive> directives) {
        this.testName = methodToTest.getMethodName() + "_happyPath";
        this.testType = TestType.HAPPY_PATH;
        this.classToTest = classToTest;
        this.methodToTest = methodToTest;
        this.directives = directives;
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
    public List<Directive> getDirectives() {
        return directives;
    }

    @Override
    public String toString() {
        return "HappyPathTestCase{" +
                "testName='" + testName + '\'' +
                ", testType=" + testType +
                ", classToTest=" + classToTest.getClassName() +
                ", methodToTest=" + methodToTest.getMethodName() +
                ", directives=" + directives +
                '}';
    }
}
