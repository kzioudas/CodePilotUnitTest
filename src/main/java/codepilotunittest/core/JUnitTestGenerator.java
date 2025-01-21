package codepilotunittest.core;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Generates JUnit tests for the given project.
 */
public class JUnitTestGenerator {

    private final ProjectRepresentation project;

    /**
     * Constructor to initialize the JUnitTestGenerator with a project representation.
     *
     * @param project The representation of the project to generate tests for.
     */
    public JUnitTestGenerator(ProjectRepresentation project) {
        this.project = project;
    }

    /**
     * Generates JUnit test classes for all test cases grouped by class.
     *
     * @param testCasesByClass Map of class names to their corresponding test cases.
     * @param projectDir        The directory of the project.
     * @throws IOException If file writing fails.
     */
    public void generateTests(Map<String, List<TestCase>> testCasesByClass, Path projectDir) throws IOException {
        for (String className : testCasesByClass.keySet()) {
            List<TestCase> testCases = testCasesByClass.get(className);
            ClassRepresentation classRepresentation = null;
            try {
                classRepresentation = project.findClass(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Calculate the output directory for the test class
            Path sourcePath = classRepresentation.getPath(); // Path of the source class file
            // Replace the main source directory with the test directory
            String sourcePathString = sourcePath.toString().replace("\\", "/");
            System.out.println("Contains " + sourcePathString.contains("src/main/java"));
            if (sourcePathString.contains("src/main/java")) {
                String testPathString = sourcePathString.replace("src/main/java", "src/test/java");
                Path testPath = Paths.get(testPathString).getParent();
                System.out.println(testPath);
                // Ensure the test directory exists
                Path testDirectory = testPath.getParent();
                Files.createDirectories(testDirectory);

                // Use TestClassGenerator to handle the generation of test classes.
                TestClassGenerator classGenerator = new TestClassGenerator(classRepresentation, testCases, testPath);
                classGenerator.generate();
            } else {
            throw new IllegalStateException("Source path does not contain expected directory structure: " + sourcePath);
        }
        }
    }

}
