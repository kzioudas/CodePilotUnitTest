package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.DirectiveParser;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A parser for reading test cases from a CSV file.
 */
public class TestCaseParser {

    private final DirectiveParser directiveParser;
    private final ProjectRepresentation project;

    public TestCaseParser(ProjectRepresentation project) {
        this.directiveParser = new DirectiveParser();
        this.project = project;
    }

    /**
     * Parses the test cases from a CSV file.
     *
     * @param filePath the path to the CSV file
     * @return a list of parsed TestCase objects
     * @throws IOException if an I/O error occurs while reading the file
     */
    public Map<String, List<TestCase>> parseTestCases(Path filePath) throws IOException {
        Map<String, List<TestCase>> testCasesByClass = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null ) {
                String[] parts = line.split(",");

                // Extract columns from CSV line
                String testName = parts[0].trim();
                String testType = parts[1].trim();
                String classToTest = parts[2].trim();
                String methodToTest = parts[3].trim();
                String directivePart = parts[4].trim();

                // Parse the directives using DirectiveParser
                List<Directive> directives = directiveParser.parse(directivePart);
                ClassRepresentation classRepresentation = project.findClass(classToTest);
                MethodRepresentation methodRepresentation = classRepresentation.findMethod(methodToTest);

                // Create the TestCase object
                TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classRepresentation, methodRepresentation, directives);

                // Add the TestCase to the appropriate list in the map
                testCasesByClass.computeIfAbsent(classToTest, k -> new ArrayList<>()).add(testCase);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        }

        return testCasesByClass;
    }

}
