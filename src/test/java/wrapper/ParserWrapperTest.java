package wrapper;

import codepilotunittest.annotations.AnnotationExtractor;
import codepilotunittest.parser.ProjectParser;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.NodeType;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.wrapper.ParserWrapper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ParserWrapperTest {

    private ProjectParser projectParser;
    private ParserWrapper parserWrapper;

    @BeforeEach
    void setUp() {
        projectParser = new ProjectParser();  // Create an actual ProjectParser object
        parserWrapper = new ParserWrapper();
    }

    @Test
    void testParseSourcePackage() {
        Path path = Path.of("src/test/resources/ParserTesting");
        // Assuming projectParser is properly implemented, parseSourcePackage will return a map.
        Map<Path, PackageNode> expectedMap = projectParser.parseSourcePackage(path);  // Use real parsing

        Map<Path, PackageNode> result = parserWrapper.parseSourcePackage(path);
        assertEquals(expectedMap.keySet(), result.keySet());
    }

    @Test
    void testCreateRelationships() {
        // Setting up the input data
        Map<Path, PackageNode> packageNodes = new HashMap<>();

        // Assuming createRelationships is properly implemented, it will return a map.
        Map<LeafNode, Set<Relationship<LeafNode>>> expectedMap = projectParser.createRelationships(packageNodes);  // Use real method

        Map<LeafNode, Set<Relationship<LeafNode>>> result = parserWrapper.createRelationships(packageNodes);
        assertEquals(expectedMap, result);
    }

    @Test
    void testIdentifyPackageNodeRelationships() {
        // Setting up the input data
        Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships = new HashMap<>();

        // Assuming identifyPackageNodeRelationships is properly implemented, it will return a map.
        Map<PackageNode, Set<Relationship<PackageNode>>> expectedMap = projectParser.identifyPackageNodeRelationships(leafNodeRelationships);  // Use real method

        Map<PackageNode, Set<Relationship<PackageNode>>> result = parserWrapper.identifyPackageNodeRelationships(leafNodeRelationships);
        assertEquals(expectedMap, result);
    }


}
