package codepilotunittest.testcases;
import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

public class HappyPathTestCase implements TestCase {
    private String testName;
    private TestType testType;
    private ClassRepresentation classToTest;
    private MethodRepresentation methodToTest;
    private List<Directive> directives;

    public HappyPathTestCase(ClassRepresentation classToTest, MethodRepresentation methodToTest, List<Directive> directives) {
        //this.testName = testName; make it with method name
        this.testType = TestType.HAPPY_PATH; // Setting test type as "happypath"
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