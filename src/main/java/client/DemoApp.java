package client;

import codepilotunittest.core.MainEngine;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DemoApp {
    private MainEngine mainEngine;
    private ProjectRepresentation projectRepresentation;

    public void setUp(Path sourcePackagePath, Path sourcePackageTestPath) throws IOException {
        mainEngine = new MainEngine(sourcePackagePath, "example-project", sourcePackageTestPath);
        projectRepresentation = mainEngine.getProjectRepresentation();

        // Έναρξη μέτρησης χρόνου
        long startTime = System.nanoTime();

        Map<String, List<TestCase>> testCases = mainEngine.getTestCases();
        mainEngine.generateTests(testCases);

        // Τέλος μέτρησης χρόνου
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000; // Μετατροπή σε milliseconds

        System.out.println("Χρόνος εκτέλεσης για τη δημιουργία των Unit Tests: " + durationMs + " ms");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DemoApp app = new DemoApp();

        // Check if paths are provided via command-line arguments
        if (args.length == 2) {
            Path sourcePackagePath = Path.of(args[0]);
            Path sourcePackageTestPath = Path.of(args[1]);
            app.setUp(sourcePackagePath, sourcePackageTestPath);
        } else {
            // Use a scanner to get paths dynamically from the user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the source package path (e.g., src/test/resources/example-project):");
            Path sourcePackagePath = Path.of(scanner.nextLine().trim());
            System.out.println("Enter the test cases CSV path (e.g., src/test/resources/example-project/testcases.csv):");
            Path sourcePackageTestPath = Path.of(scanner.nextLine().trim());

            app.setUp(sourcePackagePath, sourcePackageTestPath);
        }

        // Output project representation and test cases
//        System.out.println("Project Representation:");
//        System.out.println(app.projectRepresentation);
//        System.out.println("\nTest Cases:");
//        System.out.println(app.mainEngine.getTestCases());
    }
}
