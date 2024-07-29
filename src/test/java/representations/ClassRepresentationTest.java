package representations;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassRepresentationTest {

    private ClassRepresentation classRepresentation;
    private MethodRepresentation methodRepresentation;
    private Relationship<LeafNode> relationship;

    @BeforeEach
    void setUp() {
        methodRepresentation = mock(MethodRepresentation.class);
        relationship = mock(Relationship.class);

        List<String> modifiers = Arrays.asList("public", "abstract");
        List<String> interfaces = Arrays.asList("Serializable", "Cloneable");
        List<MethodRepresentation> methods = Arrays.asList(methodRepresentation);
        Set<Relationship<LeafNode>> relationships = new HashSet<>(Arrays.asList(relationship));
        List<String> annotations = Arrays.asList("NotNull");
        classRepresentation = new ClassRepresentation("MyClass", modifiers, interfaces, methods, relationships,annotations);
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

    @Test
    void testToString() {
        when(methodRepresentation.toString()).thenReturn("public void myMethod() {}");
//        when(relationship.getType()).thenReturn("association");
//        when(relationship.getTarget()).thenReturn(mock(LeafNode.class));

        String result = classRepresentation.toString();
        assertNotNull(result);
        assertTrue(result.contains("public abstract class MyClass"));
        assertTrue(result.contains("implements Serializable, Cloneable"));
        assertTrue(result.contains("public void myMethod() {}"));
        assertTrue(result.contains("Relationships:"));
    }
}
