package codepilotunittest.testcases;
import codepilotunittest.directives.Directive;

import java.util.List;

public class RainyDayTestCase implements TestCase {
    private String testName;
    private TestType testType;
    private String classToTest;
    private String methodToTest;
    private List<Directive> directives;

    public RainyDayTestCase(String testName, String classToTest, String methodToTest, List<Directive> directives) {
        this.testName = testName;
        this.testType = TestType.RAINY_DAY; // Setting test type as "rainyday"
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