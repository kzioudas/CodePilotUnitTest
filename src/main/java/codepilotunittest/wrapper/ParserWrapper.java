package codepilotunittest.wrapper;

import codepilotunittest.annotations.AnnotationExtractor;
import codepilotunittest.parser.ProjectParser;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParserWrapper {
    private final ProjectParser projectParser;

    public ParserWrapper() {
        this.projectParser = new ProjectParser();
    }

    /**
     * Parses the source package to obtain package nodes.
     *
     * @param sourcePackagePath The path to the source package.
     * @return A map of package nodes.
     */
    public Map<Path, PackageNode> parseSourcePackage(Path sourcePackagePath) {
        return projectParser.parseSourcePackage(sourcePackagePath);
    }

    /**
     * Creates relationships between leaf nodes.
     *
     * @param packageNodes The parsed package nodes.
     * @return A map of relationships between leaf nodes.
     */
    public Map<LeafNode, Set<Relationship<LeafNode>>> createRelationships(Map<Path, PackageNode> packageNodes) {
        return projectParser.createRelationships(packageNodes);
    }

    /**
     * Identifies relationships between package nodes.
     *
     * @param leafNodeRelationships The relationships between leaf nodes.
     * @return A map of relationships between package nodes.
     */
    public Map<PackageNode, Set<Relationship<PackageNode>>> identifyPackageNodeRelationships(Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships) {
        return projectParser.identifyPackageNodeRelationships(leafNodeRelationships);
    }

    /**
     * Extracts test annotations for methods in a leaf node.
     *
     * @param leafNode The leaf node.
     * @return A list of lists containing test annotations for each method.
     */
    public List<List<String>> getMethodTestAnnotations(LeafNode leafNode) {
        return leafNode.methods().stream()
                .map(method -> {
                    CompilationUnit cu = AnnotationExtractor.parse(method.toString());
                    MethodDeclaration methodDeclaration = cu.findFirst(MethodDeclaration.class).get();
                    return AnnotationExtractor.extractMethodAnnotations(methodDeclaration);
                })
                .collect(Collectors.toList());
    }

    /**
     * Extracts test annotations for a class in a leaf node.
     *
     * @param leafNode The leaf node.
     * @return A list containing test annotations for the class.
     */
    public List<String> getClassTestAnnotations(LeafNode leafNode) {
        CompilationUnit cu = AnnotationExtractor.parse(leafNode.toString());
        ClassOrInterfaceDeclaration classDeclaration = cu.findFirst(ClassOrInterfaceDeclaration.class).get();
        return AnnotationExtractor.extractClassAnnotations(classDeclaration);
    }
}
