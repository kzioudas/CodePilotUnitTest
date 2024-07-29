package representations;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.MethodRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MethodRepresentationTest {

    private MethodRepresentation methodRepresentation;
    private Relationship<LeafNode> relationship;

    @BeforeEach
    void setUp() {
        relationship = mock(Relationship.class);

        List<String> parameters = Arrays.asList("String param1", "int param2");
        List<String> modifiers = Arrays.asList("public", "static");
        Set<Relationship<LeafNode>> relationships = new HashSet<>(Arrays.asList(relationship));
        List<String> annotations = Arrays.asList("NotNull");
        methodRepresentation = new MethodRepresentation("myMethod", "void", parameters, modifiers, relationships,annotations);
    }

    @Test
    void testGetMethodName() {
        assertEquals("myMethod", methodRepresentation.getMethodName());
    }

    @Test
    void testGetReturnType() {
        assertEquals("void", methodRepresentation.getReturnType());
    }

    @Test
    void testGetParameters() {
        List<String> parameters = methodRepresentation.getParameters();
        assertEquals(2, parameters.size());
        assertTrue(parameters.contains("String param1"));
        assertTrue(parameters.contains("int param2"));
    }

    @Test
    void testGetModifiers() {
        List<String> modifiers = methodRepresentation.getModifiers();
        assertEquals(2, modifiers.size());
        assertTrue(modifiers.contains("public"));
        assertTrue(modifiers.contains("static"));
    }

    @Test
    void testGetRelationships() {
        Set<Relationship<LeafNode>> relationships = methodRepresentation.getRelationships();
        assertEquals(1, relationships.size());
        assertTrue(relationships.contains(relationship));
    }


}
