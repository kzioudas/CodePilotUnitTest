package client;

import codepilotunittest.core.MainEngine;
//import codepilotunittest.parser.tree.LeafNode;
//import codepilotunittest.parser.tree.PackageNode;
//import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.representations.ProjectRepresentation;

import java.io.IOException;
import java.nio.file.Path;
//import java.util.Map;
//import java.util.Set;

public class DemoApp {
    private MainEngine mainEngine;
//    private Map<Path, PackageNode> packageNodes;
//    private Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
//    private Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships;

    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;

    public void setUp() throws IOException {
        sourcePackagePath = Path.of("src/test/resources/LatexEditor");
        Path sourcePackageTestPath = Path.of("src/test/resources/LatexEditor/testcases.csv");
        mainEngine = new MainEngine(sourcePackagePath,"LatexEditor",sourcePackageTestPath);
        projectRepresentation = mainEngine.getProjectRepresentation();

    }

    public static void main(String[] args) throws IOException {
        // Create an instance of DemoApp
        DemoApp app = new DemoApp();

        // Call the setUp method to initialize fields
        app.setUp();

        // Now you can access projectRepresentation through the app instance
        System.out.println(app.projectRepresentation);
        System.out.println(app.mainEngine.getTestCases().toString());
    }
}