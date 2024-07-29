package wrapper;

import codepilotunittest.annotations.AnnotationExtractor;
import codepilotunittest.parser.ProjectParser;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.wrapper.ParserWrapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class ParserWrapperTest {

    private ProjectParser projectParser;
    private ParserWrapper parserWrapper;

    @BeforeEach
    void setUp() {
        projectParser = mock(ProjectParser.class);
        parserWrapper = new ParserWrapper();
    }

    @Test
    void testParseSourcePackage() {
        Path path = Path.of("src/test/resources");
        Map<Path, PackageNode> expectedMap = new HashMap<>();
        when(projectParser.parseSourcePackage(any(Path.class))).thenReturn(expectedMap);

        Map<Path, PackageNode> result = parserWrapper.parseSourcePackage(path);
        assertEquals(expectedMap, result);
        verify(projectParser).parseSourcePackage(path);
    }

    @Test
    void testCreateRelationships() {
        Map<Path, PackageNode> packageNodes = new HashMap<>();
        Map<LeafNode, Set<Relationship<LeafNode>>> expectedMap = new HashMap<>();
        when(projectParser.createRelationships(any(Map.class))).thenReturn(expectedMap);

        Map<LeafNode, Set<Relationship<LeafNode>>> result = parserWrapper.createRelationships(packageNodes);
        assertEquals(expectedMap, result);
        verify(projectParser).createRelationships(packageNodes);
    }

    @Test
    void testIdentifyPackageNodeRelationships() {
        Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships = new HashMap<>();
        Map<PackageNode, Set<Relationship<PackageNode>>> expectedMap = new HashMap<>();
        when(projectParser.identifyPackageNodeRelationships(any(Map.class))).thenReturn(expectedMap);

        Map<PackageNode, Set<Relationship<PackageNode>>> result = parserWrapper.identifyPackageNodeRelationships(leafNodeRelationships);
        assertEquals(expectedMap, result);
        verify(projectParser).identifyPackageNodeRelationships(leafNodeRelationships);
    }

    @Test
    void testGetMethodTestAnnotations() {
        LeafNode leafNode = mock(LeafNode.class);
        MethodDeclaration methodDeclaration = mock(MethodDeclaration.class);
        CompilationUnit compilationUnit = mock(CompilationUnit.class);


        when(AnnotationExtractor.parse(any(String.class))).thenReturn(compilationUnit);
        when(compilationUnit.findFirst(MethodDeclaration.class)).thenReturn(Optional.of(methodDeclaration));
        when(AnnotationExtractor.extractMethodAnnotations(any(MethodDeclaration.class))).thenReturn(Arrays.asList("TestAnnotation"));

        List<List<String>> result = parserWrapper.getMethodTestAnnotations(leafNode);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestAnnotation", result.get(0).get(0));
    }

    @Test
    void testGetClassTestAnnotations() {
        LeafNode leafNode = mock(LeafNode.class);
        ClassOrInterfaceDeclaration classDeclaration = mock(ClassOrInterfaceDeclaration.class);
        CompilationUnit compilationUnit = mock(CompilationUnit.class);

        when(AnnotationExtractor.parse(any(String.class))).thenReturn(compilationUnit);
        when(compilationUnit.findFirst(ClassOrInterfaceDeclaration.class)).thenReturn(Optional.of(classDeclaration));
        when(AnnotationExtractor.extractClassAnnotations(any(ClassOrInterfaceDeclaration.class))).thenReturn(Arrays.asList("ClassAnnotation"));

        List<String> result = parserWrapper.getClassTestAnnotations(leafNode);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ClassAnnotation", result.get(0));
    }
}
