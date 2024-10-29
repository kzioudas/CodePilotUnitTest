package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;

public class TestCaseFactory {

    public static TestCase createTestCase(String testName, String testType, ClassRepresentation classToTest, MethodRepresentation methodToTest, List<Directive> directives) {
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