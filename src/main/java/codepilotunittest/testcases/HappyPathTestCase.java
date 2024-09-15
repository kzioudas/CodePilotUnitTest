package codepilotunittest.testcases;
import java.util.List;

public class HappyPathTestCase implements TestCase {
    private String testName;
    private String testType;
    private String classToTest;
    private String methodToTest;
    private List<String> directives;

    public HappyPathTestCase(String testName, String classToTest, String methodToTest, List<String> directives) {
        this.testName = testName;
        this.testType = "happypath"; // Setting test type as "happypath"
        this.classToTest = classToTest;
        this.methodToTest = methodToTest;
        this.directives = directives;
    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Override
    public String getTestType() {
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
    public List<String> getDirectives() {
        return directives;
    }

}