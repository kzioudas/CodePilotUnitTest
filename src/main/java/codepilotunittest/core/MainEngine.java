package codepilotunittest.core;

import codepilotunittest.parser.factory.Parser;
import codepilotunittest.parser.factory.ParserType;
import codepilotunittest.parser.factory.ProjectParserFactory;
import codepilotunittest.parser.tree.*;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.parser.tree.LeafNode.Method;
import codepilotunittest.testcases.TestCase;
import codepilotunittest.testcases.TestCaseParser;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainEngine {
    ParserType parserType = ParserType.JAVAPARSER;
    private Parser parser = ProjectParserFactory.createProjectParser(parserType);
    private Map<Path, PackageNode> packageNodes;
    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
    private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;
    private ProjectRepresentation projectRepresentation;
    private TestCaseParser testCaseParser;
    private Map<String, List<TestCase>> testCases;
    public MainEngine(Path sourcePackagePath,String projectName) {
        this.packageNodes = parser.parseSourcePackage(sourcePackagePath);
        // Create relationships between leaf nodes (e.g., classes and methods)
        this.leafNodeRelationships = parser.createRelationships(packageNodes);
        // Identify relationships between package nodes
        this.packageNodeRelationships = parser.identifyPackageNodeRelationships(leafNodeRelationships);
        // Build a representation of the entire project
        this.projectRepresentation = buildProjectRepresentation(projectName, packageNodes, packageNodeRelationships, leafNodeRelationships);
    }

    public MainEngine(Path sourcePackagePath,String projectName,Path testCasesPath) throws IOException {
        this.packageNodes = parser.parseSourcePackage(sourcePackagePath);
        // Create relationships between leaf nodes (e.g., classes and methods)
        this.leafNodeRelationships = parser.createRelationships(packageNodes);
        // Identify relationships between package nodes
        this.packageNodeRelationships = parser.identifyPackageNodeRelationships(leafNodeRelationships);
        // Build a representation of the entire project
        this.projectRepresentation = buildProjectRepresentation( projectName, packageNodes, packageNodeRelationships, leafNodeRelationships);
        this.testCaseParser = new TestCaseParser(projectRepresentation);
        this.testCases = testCaseParser.parseTestCases(testCasesPath);
    }

    public ProjectRepresentation getProjectRepresentation(){return projectRepresentation;}

    public Map<Path, PackageNode> getPackageNodes() {
        return packageNodes;
    }

    public Map<LeafNode, Set<Relationship<LeafNode>>> getLeafNodeRelationships() {
        return leafNodeRelationships;
    }

    public Map<PackageNode, Set<Relationship<PackageNode>>> getPackageNodeRelationships() {
        return packageNodeRelationships;
    }

    public Map<String, List<TestCase>> getTestCases() {
        return testCases;
    }

    /**
     * Builds a representation of the project, including classes and their methods with their relationships.
     *
     * @param projectName                The name of the project.
     * @param packageNodes               The parsed package nodes.
     * @param packageNodeRelationships   The relationships between package nodes.
     * @param leafNodeRelationships      The relationships between leaf nodes.
     * @return                           A ProjectRepresentation object.
     */
    private static ProjectRepresentation buildProjectRepresentation(String projectName, Map<Path, PackageNode> packageNodes, Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships, Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships) {
        List<ClassRepresentation> classRepresentations = new ArrayList<>();

        // Iterate through each package node to gather class and method information
        for (PackageNode packageNode : packageNodes.values()) {
            // Build class representation for each leaf node
            for (Map.Entry<String, LeafNode> entry : packageNode.getLeafNodes().entrySet()) {
                LeafNode leafNode = entry.getValue();
                classRepresentations.add(buildClassRepresentation(leafNode, leafNodeRelationships));
            }
        }
        // Collect all project-level relationships
        Set<Relationship<PackageNode>> projectRelationships = packageNodeRelationships.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
        // Create and return a ProjectRepresentation object
        return new ProjectRepresentation(projectName, classRepresentations, projectRelationships);
    }

    /**
     * Builds a representation of a class, including its methods and their relationships.
     *
     * @param leafNode              The leaf node representing the class.
     * @param leafNodeRelationships The relationships between leaf nodes.
     * @return                      A ClassRepresentation object.
     */
    private static ClassRepresentation buildClassRepresentation(LeafNode leafNode, Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships) {
        List<MethodRepresentation> methodRepresentations = new ArrayList<>();
        // Create MethodRepresentation objects for each method in the class
        for (Method method : leafNode.getMethods()) {
            Set<Relationship<LeafNode>> methodRelationships = leafNodeRelationships.getOrDefault(leafNode, Set.of());
            methodRepresentations.add(buildMethodRepresentation(method, methodRelationships));
        }

        List<NodeType> classModifiers = new ArrayList<>();
                classModifiers.add(leafNode.getNodeType());
        List<String> interfaces = leafNode.getImplementedInterfaces();
        Set<Relationship<LeafNode>> classRelationships = leafNodeRelationships.getOrDefault(leafNode, Set.of());
        // Create and return a ClassRepresentation object
        return new ClassRepresentation(
                leafNode.getNodeName(),
                classModifiers,
                interfaces,
                methodRepresentations,
                classRelationships,
                leafNode.getPath()
        );
    }

    /**
     * Builds a representation of a method, including its relationships and test annotations.
     *
     * @param method            The method record representing the method.
     * @param relationships     The relationships of the method.
     * @return                  A MethodRepresentation object.
     */
    private static MethodRepresentation buildMethodRepresentation(Method method, Set<Relationship<LeafNode>> relationships) {
        Map<String, String> parameters = method.getParameters();
        List<ModifierType> modifiers = new ArrayList<>();
        modifiers.add(method.getMethodModifierType());
        return new MethodRepresentation(
                method.getMethodName(),
                method.getMethodReturnType(),
                parameters,
                modifiers,
                relationships
        );
    }
    
    public void generateTests(Map<String, List<TestCase>> testCasesByClass) throws IOException {
    	JUnitTestGenerator myJunitTestGenerator = new JUnitTestGenerator(this.projectRepresentation); 
    	myJunitTestGenerator.generateTests(testCasesByClass);
    }
    
}
