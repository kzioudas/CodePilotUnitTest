package testcases;

import codepilotunittest.testcases.TestCase;
import codepilotunittest.testcases.TestCaseFactory;
import codepilotunittest.testcases.HappyPathTestCase;
import codepilotunittest.testcases.RainyDayTestCase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TestCaseFactoryTest {

    @Test
    public void testCreateRainyDayTestCase() {
        // Arrange
        String testName = "rainyTest";
        String testType = "rainyday";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<String> directives = Arrays.asList("dir1", "dir2");

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);

        // Assert
        assertTrue(testCase instanceof RainyDayTestCase);
        assertEquals("rainyTest", testCase.getTestName());
        assertEquals("rainyday", testCase.getTestType());
        assertEquals("com.package.ClassToTest", testCase.getClassToTest());
        assertEquals("testMethod", testCase.getMethodToTest());
        assertEquals(directives, testCase.getDirectives());
    }

    @Test
    public void testCreateHappyPathTestCase() {
        // Arrange
        String testName = "happyTest";
        String testType = "happypath";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<String> directives = Arrays.asList("dirA", "dirB");

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);

        // Assert
        assertTrue(testCase instanceof HappyPathTestCase);
        assertEquals("happyTest", testCase.getTestName());
        assertEquals("happypath", testCase.getTestType());
        assertEquals("com.package.ClassToTest", testCase.getClassToTest());
        assertEquals("testMethod", testCase.getMethodToTest());
        assertEquals(directives, testCase.getDirectives());
    }

    @Test
    public void testUnknownTestTypeThrowsException() {
        // Arrange
        String testName = "unknownTest";
        String testType = "unknown";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<String> directives = Arrays.asList("dirX", "dirY");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);
        });

        assertEquals("Unknown test type: unknown", exception.getMessage());
    }
}