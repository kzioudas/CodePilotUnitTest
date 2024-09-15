package codepilotunittest.testcases;

import java.util.List;

public interface TestCase {
    String getTestName();

    String getTestType();

    String getClassToTest();

    String getMethodToTest();

    List<String> getDirectives();
}
