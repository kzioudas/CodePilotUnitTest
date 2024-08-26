package representations;

import codepilotunittest.parser.tree.*;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClassRepresentationTest {

    private ClassRepresentation classRepresentation;
    private MethodRepresentation methodRepresentation;
    private Relationship<LeafNode> relationship;

    @BeforeEach
    void setUp() {
        // Create a simple LeafNode (adjust the constructor as per your actual LeafNode implementation)
        LeafNode.Method method1 = new LeafNode.Method("method1", "String", ModifierType.PUBLIC, Map.of());
        LeafNode.Method method2 = new LeafNode.Method("method2", "int", ModifierType.PUBLIC, Map.of());
        LeafNode startingNode = new LeafNode(
                Path.of("/some/path"),
                "TestNode",
                NodeType.CLASS,
                "Object",
                null,
                List.of(),
                List.of(method1, method2),
                List.of(),
                Map.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );


        LeafNode endingNode = new LeafNode(
                Path.of("/some/path"),
                "TestNode2",
                NodeType.CLASS,
                "Object",
                null,
                List.of(),
                List.of(method1, method2),
                List.of(),
                Map.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of());

        // Create a relationship using the Relationship and RelationshipType
        relationship = new Relationship<>(startingNode, endingNode, RelationshipType.ASSOCIATION);

        // Relationships for the method
        Set<Relationship<LeafNode>> methodRelationships = new HashSet<>(Arrays.asList(relationship));

        // Create a method representation
        methodRepresentation = new MethodRepresentation(
                "myMethod",
                "void",
                Map.of(), // parameters
                Arrays.asList(ModifierType.get("public")), // modifiers
                methodRelationships,
                Arrays.asList() // testAnnotations
        );

        // Relationships for the class
        Set<Relationship<LeafNode>> classRelationships = new HashSet<>(Arrays.asList(relationship));

        // Create a class representation
        classRepresentation = new ClassRepresentation(
                "MyClass",
                Arrays.asList("public", "abstract"), // modifiers
                Arrays.asList("Serializable", "Cloneable"), // interfaces
                Arrays.asList(methodRepresentation), // methods
                classRelationships,
                Arrays.asList("NotNull") // classTestAnnotations
        );
    }

    @Test
    void testGetClassName() {
        assertEquals("MyClass", classRepresentation.getClassName());
    }

    @Test
    void testGetModifiers() {
        List<String> modifiers = classRepresentation.getModifiers();
        assertEquals(2, modifiers.size());
        assertTrue(modifiers.contains("public"));
        assertTrue(modifiers.contains("abstract"));
    }

    @Test
    void testGetInterfaces() {
        List<String> interfaces = classRepresentation.getInterfaces();
        assertEquals(2, interfaces.size());
        assertTrue(interfaces.contains("Serializable"));
        assertTrue(interfaces.contains("Cloneable"));
    }

    @Test
    void testGetMethods() {
        List<MethodRepresentation> methods = classRepresentation.getMethods();
        assertEquals(1, methods.size());
        assertEquals(methodRepresentation, methods.get(0));
    }

    @Test
    void testGetRelationships() {
        Set<Relationship<LeafNode>> relationships = classRepresentation.getRelationships();
        assertEquals(1, relationships.size());
        assertTrue(relationships.contains(relationship));
    }

//    @Test
//    void testToString() {
//        String result = classRepresentation.toString();
//        assertNotNull(result);
//        assertTrue(result.contains("public abstract class MyClass"));
//        assertTrue(result.contains("implements Serializable, Cloneable"));
//        assertTrue(result.contains("public void myMethod()"));
// }

    @Test
    void testProjectRepresentation() {
        Set<Relationship<PackageNode>> projectRelationships = new HashSet<>();
        List<ClassRepresentation> classes = Arrays.asList(classRepresentation);

        ProjectRepresentation projectRepresentation = new ProjectRepresentation(
                "MyProject",
                classes,
                projectRelationships
        );

        assertEquals("MyProject", projectRepresentation.getProjectName());
        assertEquals(1, projectRepresentation.getClasses().size());
        assertEquals(classRepresentation, projectRepresentation.getClasses().get(0));
        assertTrue(projectRepresentation.getRelationships().isEmpty());
    }
}
