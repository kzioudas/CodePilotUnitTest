package core;

import codepilotunittest.core.MainEngine;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.wrapper.ParserWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PathTemplate.LatexEditor;
import utils.PathTemplate.ParserTesting;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MainEngineTest {

    private MainEngine mainEngine;
    private ParserWrapper parserWrapper;
    private Map<Path, PackageNode> packageNodes;
    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
    private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;

    @BeforeEach
    void setUp() {


        // Create dummy package nodes and relationships

        // Create the main engine instance
        mainEngine = new MainEngine(LatexEditor.SRC.path.toString());
        packageNodes = mainEngine.getPackageNodes();
        leafNodeRelationships = mainEngine.getLeafNodeRelationships();
        packageNodeRelationships = mainEngine.getPackageNodeRelationships();


    }

    @Test
    void testBuildProjectRepresentation() {
        // When
        ProjectRepresentation projectRepresentation = MainEngine.buildProjectRepresentation(
                "MyProject", packageNodes, packageNodeRelationships, leafNodeRelationships, parserWrapper
        );

        // Then
        assertNotNull(projectRepresentation);
        assertEquals("MyProject", projectRepresentation.getProjectName());
        assertEquals(1, projectRepresentation.getClasses().size());

        ClassRepresentation classRepresentation = projectRepresentation.getClasses().get(0);
        assertEquals("TestClass", classRepresentation.getClassName());
        assertEquals(1, classRepresentation.getMethods().size());

        MethodRepresentation methodRepresentation = classRepresentation.getMethods().get(0);
        assertEquals("testMethod", methodRepresentation.getMethodName());
        assertEquals("void", methodRepresentation.getReturnType());
        assertEquals(1, methodRepresentation.getParameters().size());
        assertEquals("param1", methodRepresentation.getParameters().get(0));
    }

    @Test
    void testBuildClassRepresentation() {
        // Given
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("TestClass");

        // When
        ClassRepresentation classRepresentation = MainEngine.buildClassRepresentation(leafNode, leafNodeRelationships, parserWrapper);

        // Then
        assertNotNull(classRepresentation);
        assertEquals("TestClass", classRepresentation.getClassName());
        //assertEquals(1, classRepresentation.getMethodRepresentations().size());
    }

    @Test
    void testBuildMethodRepresentation() {
        // Given
        LeafNode leafNode = packageNodes.values().iterator().next().getLeafNodes().get("TestClass");
        LeafNode.Method method = leafNode.methods().get(0);
        Set<Relationship<LeafNode>> relationships = leafNodeRelationships.get(leafNode);
        //List<String> testAnnotations = List.of(); // Assume no annotations for simplicity

        // When
        MethodRepresentation methodRepresentation = MainEngine.buildMethodRepresentation(method, relationships, null);

        // Then
        assertNotNull(methodRepresentation);
        assertEquals("testMethod", methodRepresentation.getMethodName());
        assertEquals("void", methodRepresentation.getReturnType());
        assertEquals(1, methodRepresentation.getParameters().size());
        assertEquals("param1", methodRepresentation.getParameters().get(0));
        assertEquals(List.of("PUBLIC"), methodRepresentation.getModifiers());
        //assertEquals(testAnnotations, methodRepresentation.getTestAnnotations());
    }
}
