package codepilotunittest.core;

import java.util.ArrayList;
import java.util.List;

public class TestCases {
    private List<TestCase> testCaseList = new ArrayList<>();

    public void addTestCase(TestCase testCase) {
        testCaseList.add(testCase);
    }

    public TestCase[] getTestCaseList() {
        return testCaseList.toArray(new TestCase[testCaseList.size()]);
    }
}
