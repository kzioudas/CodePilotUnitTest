package core;

import codepilotunittest.core.MainEngine;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.ModifierType;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Main Engine Tests")
class MainEngineTest {

    private MainEngine mainEngine;
    private Map<Path, PackageNode> packageNodes;
    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
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
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();
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
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();

        // Test with a simple class
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("InnerClassSample");

        // When
        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships);

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
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();

        // Test with an interface
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("TestingInterface");

        // When
        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships);

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
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();

        // Test with an enum
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("EnumSample");

        // When
        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships);

        // Then
        assertNotNull(classRepresentation);
        assertEquals("EnumSample", classRepresentation.getClassName());
        //assertEquals("enum", classRepresentation.getType());
        assertEquals(0, classRepresentation.getMethods().size());
    }

    @Test
    @DisplayName("Build Method Representation")
    void testBuildMethodRepresentation() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();
        projectRepresentation = mainEngine.getProjectRepresentation();
        // Test with a method having no parameters
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("ObjectCreationSample");
        LeafNode.Method method = leafNode.getMethods().get(0);
        Set<Relationship<LeafNode>> relationships = leafNodeRelationships.get(leafNode);

        // When
        MethodRepresentation methodRepresentation = MainEngine.buildMethodRepresentation(method, relationships, null);

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
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();
        projectRepresentation = mainEngine.getProjectRepresentation();
        // Test with a method having parameters
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("ObjectCreationSample");
        LeafNode.Method method = leafNode.getMethods().get(2);
        Set<Relationship<LeafNode>> relationships = leafNodeRelationships.get(leafNode);

        // When
        MethodRepresentation methodRepresentation = MainEngine.buildMethodRepresentation(method, relationships, null);

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

//    @Test
//    void testNestedClassRepresentation() {
//        // Test with a nested class
//        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("InnerClassSample");
//
//        // Assume InnerClassSample has an inner class
//        LeafNode innerLeafNode = leafNode.getInnerClasses().get("InnerClassSample$Inner");
//
//        // When
//        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(innerLeafNode, leafNodeRelationships);
//
//        // Then
//        assertNotNull(classRepresentation);
//        assertEquals("InnerClassSample$Inner", classRepresentation.getClassName());
//        assertEquals(0, classRepresentation.getMethods().size());
//    }

    @Test
    @DisplayName("Multiple Package Structure Test")
    void testMultiplePackageStructure() {
        // Test handling of multiple packages
        Path path = Path.of("src/test/resources/LatexEditor");
        mainEngine = new MainEngine(path,"LatexEditor");
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();
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

//    @Test
//    void testMalformedClassFile() {
//        // Test handling of a malformed class file (e.g., missing brackets)
//        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("MalformedClass");
//
//        // When
//        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships);
//
//        // Then
//        assertNotNull(classRepresentation);
//        assertEquals("MalformedClass", classRepresentation.getClassName());
//        assertTrue(classRepresentation.getMethods().isEmpty()); // Assuming it couldn't parse methods
//    }

    @Test
    @DisplayName("Multiple Inheritance Test")
    void testMultipleInheritance() {
        Path path = Path.of("src/test/resources/ParserTesting");
        mainEngine = new MainEngine(path,"ParserTesting");
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();
        projectRepresentation = mainEngine.getProjectRepresentation();
        // Test handling of multiple inheritance (interfaces)
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("ImplementingClass");

        // When
        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships);

        // Then
        assertNotNull(classRepresentation);
        assertEquals("ImplementingClass", classRepresentation.getClassName());
        assertEquals(List.of("TestingInterface", "TestingInterface2"), classRepresentation.getInterfaces());
    }
}
