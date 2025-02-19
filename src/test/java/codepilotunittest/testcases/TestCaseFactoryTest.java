package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.SimpleValueDirective;
import codepilotunittest.directives.ThrowsExceptionDirective;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.core.MainEngine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TestCaseFactory class.
 */
public class TestCaseFactoryTest {

    private MainEngine mainEngine;
    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;

    public void setUp() throws IOException {
        sourcePackagePath = Path.of("src/test/resources/example-project");
        mainEngine = new MainEngine(sourcePackagePath, "example-project");
        projectRepresentation = mainEngine.getProjectRepresentation();
    }

    /**
     * Tests the creation of a HappyPathTestCase.
     */
    @Test
    public void testCreateHappyPathTestCase() throws IOException, ClassNotFoundException, MethodNotFoundException {
        setUp();
        // Arrange
        String testType = "happypath";
        String classToTest = "Calculator";
        String methodToTest = "add";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("a", "10");
        parameters.put("b", "5");
        Map<String, String> cParameters = new HashMap<>();


        Directive directive = new SimpleValueDirective(parameters, "15", "True", cParameters);

        ClassRepresentation classRepresentation = projectRepresentation.findClass(classToTest);
        MethodRepresentation methodRepresentation = classRepresentation.findMethod(methodToTest);

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testType, classRepresentation, methodRepresentation, directive);

        // Assert
        assertTrue(testCase instanceof HappyPathTestCase, "Should create a HappyPathTestCase");
        assertEquals(TestType.HAPPY_PATH, testCase.getTestType(), "Test type should be HAPPY_PATH");
        assertEquals(directive, testCase.getDirective(), "Directives should match expected");
        assertEquals(classRepresentation, testCase.getClassToTest(), "Class to test should match expected");
        assertEquals(methodRepresentation, testCase.getMethodToTest(), "Method to test should match expected");
    }


    /**
     * Tests the creation of a RainyDayTestCase.
     */
    @Test
    public void testCreateRainyDayTestCase() throws IOException, ClassNotFoundException, MethodNotFoundException {
        setUp();
        // Arrange
        String testType = "rainyday";
        String classToTest = "Calculator";
        String methodToTest = "divide";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("a", "10");
        parameters.put("b", "0");
        Map<String, String> cParameters = new HashMap<>();

        Directive directive = new ThrowsExceptionDirective(parameters, "ArithmeticException", "Exception", cParameters);

        ClassRepresentation classRepresentation = projectRepresentation.findClass(classToTest);
        MethodRepresentation methodRepresentation = classRepresentation.findMethod(methodToTest);

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testType, classRepresentation, methodRepresentation, directive);

        // Assert
        assertTrue(testCase instanceof RainyDayTestCase, "Should create a RainyDayTestCase");
        assertEquals(TestType.RAINY_DAY, testCase.getTestType(), "Test type should be RAINY_DAY");
        assertEquals(directive, testCase.getDirective(), "Directives should match expected");
        assertEquals(classRepresentation, testCase.getClassToTest(), "Class to test should match expected");
        assertEquals(methodRepresentation, testCase.getMethodToTest(), "Method to test should match expected");
    }

    /**
     * Tests that an exception is thrown for an unknown test type.
     */
    @Test
    public void testUnknownTestTypeThrowsException() throws IOException, ClassNotFoundException, MethodNotFoundException {
        setUp();
        // Arrange
        String testType = "unknown";
        String classToTest = "Calculator";
        String methodToTest = "add";
        Map<String, String> parameters = Map.of("a", "10", "b", "5");
        Map<String, String> cParameters = new HashMap<>();
        Directive directive = new ThrowsExceptionDirective(parameters, "15", "Exception", cParameters);

        ClassRepresentation classRepresentation = projectRepresentation.findClass(classToTest);
        MethodRepresentation methodRepresentation = classRepresentation.findMethod(methodToTest);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TestCaseFactory.createTestCase(testType, classRepresentation, methodRepresentation, directive);
        });

        assertEquals("Unknown test type: unknown", exception.getMessage(), "Exception message should match expected");
    }
}