package client;

import codepilotunittest.core.MainEngine;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.wrapper.ParserWrapper;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class DemoApp {
    private MainEngine mainEngine;
    private Map<Path, PackageNode> packageNodes;
    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
    private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;
    private ParserWrapper parserWrapper;
    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;

    public void setUp(){
        ParserWrapper parserWrapper = new ParserWrapper();
        sourcePackagePath = Path.of("src");
        packageNodes = parserWrapper.parseSourcePackage(sourcePackagePath);
        leafNodeRelationships = parserWrapper.createRelationships(packageNodes);
        packageNodeRelationships = parserWrapper.identifyPackageNodeRelationships(leafNodeRelationships);
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