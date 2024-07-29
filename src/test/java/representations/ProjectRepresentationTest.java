package representations;

import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectRepresentationTest {

    private ProjectRepresentation projectRepresentation;
    private ClassRepresentation classRepresentation;
    private Relationship<PackageNode> relationship;

    @BeforeEach
    void setUp() {
        classRepresentation = mock(ClassRepresentation.class);
        relationship = mock(Relationship.class);

        List<ClassRepresentation> classes = Arrays.asList(classRepresentation);
        Set<Relationship<PackageNode>> relationships = new HashSet<>(Arrays.asList(relationship));

        projectRepresentation = new ProjectRepresentation("MyProject", classes, relationships);
    }

    @Test
    void testGetProjectName() {
        assertEquals("MyProject", projectRepresentation.getProjectName());
    }

    @Test
    void testGetClasses() {
        List<ClassRepresentation> classes = projectRepresentation.getClasses();
        assertEquals(1, classes.size());
        assertEquals(classRepresentation, classes.get(0));
    }

    @Test
    void testGetRelationships() {
        Set<Relationship<PackageNode>> relationships = projectRepresentation.getRelationships();
        assertEquals(1, relationships.size());
        assertTrue(relationships.contains(relationship));
    }


}
