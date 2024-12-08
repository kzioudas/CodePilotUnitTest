package codepilotunittest.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;

/**
 * Orchestrates the generation of JUnit test classes for a project.
 */
public class JUnitTestGenerator {

    private final ProjectRepresentation project;

    /**
     * Constructor for JUnitTestGenerator.
     *
     * @param project The project representation containing class and method details.
     */
    public JUnitTestGenerator(ProjectRepresentation project) {
        this.project = project;
    }

    /**
     * Generates JUnit test classes for the given test cases.
     *
     * @param testCasesByClass A map of class names to their test cases.
     * @param outputDir The directory where the test files will be written.
     * @throws IOException If there is an error writing the test files.
     */
    public void generateTests(Map<String, List<TestCase>> testCasesByClass, Path outputDir) throws IOException {
        for (Map.Entry<String, List<TestCase>> entry : testCasesByClass.entrySet()) {
            String className = entry.getKey();
            List<TestCase> testCases = entry.getValue();

            ClassRepresentation classRepresentation = null;
            try {
                classRepresentation = project.findClass(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            TestClassGenerator classGenerator = new TestClassGenerator(classRepresentation, testCases, outputDir);
            classGenerator.generate();
        }
    }
}
