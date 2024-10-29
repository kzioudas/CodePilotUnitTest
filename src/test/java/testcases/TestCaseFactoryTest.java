package testcases;

import codepilotunittest.core.MainEngine;
import codepilotunittest.directives.Directive;
import codepilotunittest.directives.NotNullDirective;
import codepilotunittest.directives.SimpleValueDirective;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TestCaseFactory class.
 */
public class TestCaseFactoryTest {
    private MainEngine mainEngine;
    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;
    private ClassRepresentation classRepresentation;
    private MethodRepresentation methodRepresentation;
    public void setUp() throws IOException {
        sourcePackagePath = Path.of("src/test/resources/LatexEditor");
        mainEngine = new MainEngine(sourcePackagePath,"LatexEditor");
        projectRepresentation = mainEngine.getProjectRepresentation();

    }

    /**
     * Tests the creation of a RainyDayTestCase.
     */
    @Test
    public void testCreateHappyPathTestCase() throws MethodNotFoundException {
        // Arrange
        try {
            setUp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String testName = "VersionsManager";
        String testType = "happypath";
        String classToTest = "VersionsManager";
        String methodToTest = "setStrategy";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("strategy"),
                new SimpleValueDirective("strategy","strategy")
        );
        try {
            classRepresentation = projectRepresentation.findClass(classToTest);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            methodRepresentation = classRepresentation.findMethod(methodToTest);
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classRepresentation, methodRepresentation, directives);

        // Assert
        assertTrue(testCase instanceof HappyPathTestCase);
        assertEquals("VersionsManager", testCase.getTestName());
        assertEquals(TestType.HAPPY_PATH, testCase.getTestType());
        try {
            assertEquals(projectRepresentation.findClass("VersionsManager"), testCase.getClassToTest());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(projectRepresentation.findClass("VersionsManager").findMethod("setStrategy"), testCase.getMethodToTest());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(directives, testCase.getDirectives());
    }

    /**
     * Tests the creation of a HappyPathTestCase.
     */
    @Test
    public void testCreateRainyDayTestCase() {
        try {
            setUp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Arrange
        String testName = "testInitializeEditor";
        String testType = "rainyday";
        String classToTest = "LatexEditorController";
        String methodToTest = "LatexEditorController";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("strategy"),
                new SimpleValueDirective("strategy", "strategy")
        );

        try {
            classRepresentation = projectRepresentation.findClass(classToTest);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            methodRepresentation = classRepresentation.findMethod(methodToTest);
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classRepresentation, methodRepresentation, directives);

        // Assert
        assertTrue(testCase instanceof RainyDayTestCase);
        assertEquals("testInitializeEditor", testCase.getTestName());
        assertEquals(TestType.RAINY_DAY, testCase.getTestType());
        try {
            assertEquals(projectRepresentation.findClass("LatexEditorController"), testCase.getClassToTest());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(projectRepresentation.findClass("LatexEditorController").findMethod("LatexEditorController"), testCase.getMethodToTest());
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(directives, testCase.getDirectives());
    }

    /**
     * Tests that an exception is thrown for an unknown test type.
     */
    @Test
    public void testUnknownTestTypeThrowsException() {
        try {
            setUp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Arrange
        String testName = "unknownTest";
        String testType = "unknown";
        String classToTest = "com.package.ClassToTest";
        String methodToTest = "testMethod";
        List<Directive> directives = Arrays.asList(
                new NotNullDirective("param1")
        );

        // Act & Assert
        Exception exception = assertThrows(ClassNotFoundException.class, () -> {
            TestCaseFactory.createTestCase(testName, testType, projectRepresentation.findClass(classToTest), methodRepresentation = classRepresentation.findMethod(methodToTest), directives);
        });

        assertEquals("Class with name com.package.ClassToTest not found.", exception.getMessage());
    }
}
