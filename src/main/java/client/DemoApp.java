package client;

import codepilotunittest.core.MainEngine;
import codepilotunittest.parser.factory.Parser;
import codepilotunittest.parser.factory.ParserType;
import codepilotunittest.parser.factory.ProjectParserFactory;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ProjectRepresentation;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class DemoApp {
    private MainEngine mainEngine;
    private Map<Path, PackageNode> packageNodes;
    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
    private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;

    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;

    public void setUp(){
        ParserType parserType = ParserType.JAVAPARSER;;
       Parser parser = ProjectParserFactory.createProjectParser(parserType);
        sourcePackagePath = Path.of("src/test/resources/LatexEditor");
        packageNodes = parser.parseSourcePackage(sourcePackagePath);
        leafNodeRelationships = parser.createRelationships(packageNodes);
        packageNodeRelationships = parser.identifyPackageNodeRelationships(leafNodeRelationships);
        projectRepresentation = mainEngine.buildProjectRepresentation("MyProject", packageNodes, packageNodeRelationships, leafNodeRelationships);

    }

    public static void main(String[] args) {
        // Create an instance of DemoApp
        DemoApp app = new DemoApp();

        // Call the setUp method to initialize fields
        app.setUp();

        // Now you can access projectRepresentation through the app instance
        System.out.println(app.projectRepresentation);
    }
}