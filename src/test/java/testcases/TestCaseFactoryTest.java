package testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.NotNullDirective;
import codepilotunittest.directives.RangeDirective;
import codepilotunittest.testcases.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TestCaseFactory class.
 */
public class TestCaseFactoryTest {

    /**
     * Tests the creation of a RainyDayTestCase.
     */
    @Test
    public void testCreateRainyDayTestCase() {
        // Arrange
        String testName = "rainyTest";
        String testType = "rainyday";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("param1"),
                new RangeDirective("param2", 0, 10)
        );

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);

        // Assert
        assertTrue(testCase instanceof RainyDayTestCase);
        assertEquals("rainyTest", testCase.getTestName());
        assertEquals(TestType.RAINY_DAY, testCase.getTestType());
        assertEquals("com.package.ClassToTest", testCase.getClassToTest());
        assertEquals("testMethod", testCase.getMethodToTest());
        assertEquals(directives, testCase.getDirectives());
    }

    /**
     * Tests the creation of a HappyPathTestCase.
     */
    @Test
    public void testCreateHappyPathTestCase() {
        // Arrange
        String testName = "happyTest";
        String testType = "happypath";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("param1"),
                new RangeDirective("param2", 1, 5)
        );

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);

        // Assert
        assertTrue(testCase instanceof HappyPathTestCase);
        assertEquals("happyTest", testCase.getTestName());
        assertEquals(TestType.HAPPY_PATH, testCase.getTestType());
        assertEquals("com.package.ClassToTest", testCase.getClassToTest());
        assertEquals("testMethod", testCase.getMethodToTest());
        assertEquals(directives, testCase.getDirectives());
    }

    /**
     * Tests that an exception is thrown for an unknown test type.
     */
    @Test
    public void testUnknownTestTypeThrowsException() {
        // Arrange
        String testName = "unknownTest";
        String testType = "unknown";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("param1")
        );

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);
        });

        assertEquals("Unknown test type: unknown", exception.getMessage());
    }
}
