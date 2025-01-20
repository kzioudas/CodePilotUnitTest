package codepilotunittest.core;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;

import java.io.IOException;
import java.nio.file.Path;
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
     * @param outputDir        The directory to output the generated test files.
     * @throws IOException If file writing fails.
     */
    public void generateTests(Map<String, List<TestCase>> testCasesByClass, Path outputDir) throws IOException {
        for (String className : testCasesByClass.keySet()) {
            List<TestCase> testCases = testCasesByClass.get(className);
            ClassRepresentation classRepresentation = null;
            try {
                classRepresentation = project.findClass(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Use TestClassGenerator to handle the generation of test classes.
            TestClassGenerator classGenerator = new TestClassGenerator(classRepresentation, testCases, outputDir);
            classGenerator.generate();
        }
    }
}
