package codepilotunittest.testcases;

import java.util.List;

public class TestCaseFactory {

    public static TestCase createTestCase(String testName, String testType, String classToTest, String methodToTest, List<String> directives) {
        switch (testType.toLowerCase()) {
            case "rainyday":
                return new RainyDayTestCase(testName, classToTest, methodToTest, directives);
            case "happypath":
                return new HappyPathTestCase(testName, classToTest, methodToTest, directives);
            // Add more directive types as needed
            default:
                throw new IllegalArgumentException("Unknown test type: " + testType);
        }
    }
}