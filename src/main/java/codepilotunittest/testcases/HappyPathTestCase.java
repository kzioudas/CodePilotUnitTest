package codepilotunittest.testcases;
import codepilotunittest.directives.Directive;

import java.util.List;

public class HappyPathTestCase implements TestCase {
    private String testName;
    private TestType testType;
    private String classToTest;
    private String methodToTest;
    private List<Directive> directives;

    public HappyPathTestCase(String testName, String classToTest, String methodToTest, List<Directive> directives) {
        this.testName = testName;
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
    public String getClassToTest() {
        return classToTest;
    }

    @Override
    public String getMethodToTest() {
        return methodToTest;
    }

    @Override
    public List<Directive> getDirectives() {
        return directives;
    }

}