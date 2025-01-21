package codepilotunittest.core;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.ModifierType;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;


import codepilotunittest.testcases.TestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Main Engine Tests")
class MainEngineTest {

    private MainEngine mainEngine;
    private Map<Path, PackageNode> packageNodes;
    @SuppressWarnings("unused")
	private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;
    private ProjectRepresentation projectRepresentation;


    @Test
    @DisplayName("Build Project Representation")
    void testBuildProjectRepresentation() {
        // When
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");
        packageNodes = mainEngine.getPackageNodes();
        projectRepresentation = mainEngine.getProjectRepresentation();

        // Then
        assertNotNull(projectRepresentation);
        assertEquals("ParserTesting", projectRepresentation.getProjectName());
        assertEquals(7, projectRepresentation.getClasses().size());

        // Check that all classes are represented correctly
        List<String> expectedClassNames = List.of("InnerClassSample", "ImplementingClass",
                "TestingInterface2", "ExtensionClass", "TestingInterface", "EnumSample", "ObjectCreationSample");
        assertTrue(projectRepresentation.getClasses().stream()
                .map(ClassRepresentation::getClassName)
                .collect(Collectors.toSet())
                .containsAll(expectedClassNames));
    }

    @Test
    @DisplayName("Build Class Representation")
    void testBuildClassRepresentation() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        ClassRepresentation classRepresentation = null;
        try {
            classRepresentation = mainEngine.getProjectRepresentation().findClass("InnerClassSample");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(classRepresentation);
        assertEquals("InnerClassSample", classRepresentation.getClassName());
        assertEquals(0, classRepresentation.getMethods().size());
    }

    @Test
    @DisplayName("Build Interface Representation")
    void testBuildInterfaceRepresentation() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        ClassRepresentation classRepresentation = null;
        try {
            classRepresentation = mainEngine.getProjectRepresentation().findClass("TestingInterface");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(classRepresentation);
        assertEquals("TestingInterface", classRepresentation.getClassName());
        //assertEquals("interface", classRepresentation.getType());
        assertEquals(0, classRepresentation.getMethods().size());
    }

    @Test
    @DisplayName("Build Enum Representation")
    void testBuildEnumRepresentation() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        ClassRepresentation classRepresentation = null;
        try {
            classRepresentation = mainEngine.getProjectRepresentation().findClass("EnumSample");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(classRepresentation);
        assertEquals("EnumSample", classRepresentation.getClassName());
        assertEquals(0, classRepresentation.getMethods().size());
    }

    @Test
    @DisplayName("Build Method Representation")
    void testBuildMethodRepresentation() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        MethodRepresentation methodRepresentation = null;
        try {
            methodRepresentation = mainEngine.getProjectRepresentation().findClass("ObjectCreationSample").findMethod("ObjectCreationSample");
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(methodRepresentation);
        assertEquals("ObjectCreationSample", methodRepresentation.getMethodName());
        assertEquals("Constructor", methodRepresentation.getReturnType());
        assertEquals(0, methodRepresentation.getParameters().size());
        assertEquals(List.of(ModifierType.get("public")), methodRepresentation.getModifiers());
    }

    @Test
    @DisplayName("Build Method Representation With Parameters")
    void testBuildMethodWithParameters() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        MethodRepresentation methodRepresentation = null;
        try {
            methodRepresentation = mainEngine.getProjectRepresentation().findClass("ObjectCreationSample").findMethod("createMapWithObject");
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(methodRepresentation);
        assertEquals("createMapWithObject", methodRepresentation.getMethodName());
        assertEquals("void", methodRepresentation.getReturnType());
        assertEquals(1, methodRepresentation.getParameters().size());
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("A", "String"); // Replace "TypeA" with the actual type

        assertEquals(expectedParameters, methodRepresentation.getParameters());
        assertEquals(List.of(ModifierType.get("private")), methodRepresentation.getModifiers());
    }


    @Test
    @DisplayName("Multiple Package Structure Test")
    void testMultiplePackageStructure() {
        // Test handling of multiple packages
        Path path = Path.of("src/test/resources/LatexEditor");
        mainEngine = new MainEngine(path,"LatexEditor");
        projectRepresentation = mainEngine.getProjectRepresentation();

        assertNotNull(projectRepresentation);
        assertEquals("LatexEditor", projectRepresentation.getProjectName());
        assertTrue(projectRepresentation.getClasses().size() > 1); // more later ToDo
    }

    @Test
    @DisplayName("Empty Package Test")
    void testEmptyPackage() {
        // Test handling of an empty package
        Path path = Path.of("src/test/resources/EmptyPackage");
        mainEngine = new MainEngine(path,"EmptyPackage");
        projectRepresentation = mainEngine.getProjectRepresentation();

        assertNotNull(projectRepresentation);
        assertEquals("EmptyPackage", projectRepresentation.getProjectName());
        assertEquals(0, projectRepresentation.getClasses().size());
    }


    @Test
    @DisplayName("Multiple Inheritance Test")
    void testMultipleInheritance() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");

        // When
        ClassRepresentation classRepresentation = null;
        try {
            classRepresentation = mainEngine.getProjectRepresentation().findClass("ImplementingClass");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Then
        assertNotNull(classRepresentation);
        assertEquals("ImplementingClass", classRepresentation.getClassName());
        assertEquals(List.of("TestingInterface", "TestingInterface2"), classRepresentation.getInterfaces());
    }

    @Test
    @DisplayName("Verify Updated Test Path Mapping")
    void testUpdatedGenerateTestsPathMapping() throws IOException {
        // Arrange: Initialize MainEngine with mock project structure
        Path sourcePackagePath = Path.of("src/test/resources/example-project/src/main/java");
        Path testCasesPath = Path.of("src/test/resources/example-project/testcases.csv");
        mainEngine = new MainEngine(sourcePackagePath, "example-project", testCasesPath);


        // Define expected test directory and test file path
        Path projectDir = Path.of("src/test/resources/example-project");
        Path expectedTestPath = Path.of("src/test/resources/example-project/src/test/java/example/UserTest.java");

        // Act: Generate test classes
        mainEngine.generateTests(mainEngine.getTestCases());

        // Assert: Verify directory and file creation
        Path testDirectory = expectedTestPath.getParent();
        assertTrue(Files.exists(testDirectory), "Expected test directory to exist.");
        assertTrue(Files.exists(expectedTestPath), "Expected test file to be created.");
    }


}
