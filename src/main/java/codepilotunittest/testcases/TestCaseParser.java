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
     * @return a map of class names to their corresponding test cases
     * @throws IOException if an I/O error occurs while reading the file
     */
    public Map<String, List<TestCase>> parseTestCases(Path filePath) throws IOException {
        Map<String, List<TestCase>> testCasesByClass = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int lineNumber = 0;

            // Skip the header row
            br.readLine();
            lineNumber++;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (!isValidCsvLine(parts)) {
                    throw new IllegalArgumentException("Malformed or incomplete line at " +
                            "line " + lineNumber + ": " + line);
                }

                // Extract columns from CSV line
                String testType = parts[0].trim();
                String classToTest = parts[1].trim();
                String methodToTest = parts[2].trim();
                String directivePart = parts[3].trim();

                // Parse the directives using DirectiveParser
                List<Directive> directives = directiveParser.parse(directivePart);
                ClassRepresentation classRepresentation = project.findClass(classToTest);
                MethodRepresentation methodRepresentation = classRepresentation.findMethod(methodToTest);

                // Validate directives against method parameters
                validateDirectivesForMethod(methodRepresentation, directives);

                // Create the TestCase object
                TestCase testCase = TestCaseFactory.createTestCase(testType, classRepresentation, methodRepresentation, directives);

                // Add the TestCase to the appropriate list in the map
                testCasesByClass.computeIfAbsent(classToTest, k -> new ArrayList<>()).add(testCase);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found during test case parsing: " + e.getMessage(), e);
        } catch (MethodNotFoundException e) {
            throw new RuntimeException("Method not found during test case parsing: " + e.getMessage(), e);
        }

        return testCasesByClass;
    }

    /**
     * Validates if a CSV line has the required number of parts.
     *
     * @param parts The split line of the CSV.
     * @return true if the line is valid, false otherwise.
     */
    private boolean isValidCsvLine(String[] parts) {
        return parts.length == 4;
    }

    /**
     * Validates if all parameters of a method have corresponding directives.
     *
     * @param methodRepresentation The method being tested.
     * @param directives           The list of directives provided.
     * @throws IllegalArgumentException If any parameter lacks a directive.
     */
    private void validateDirectivesForMethod(MethodRepresentation methodRepresentation, List<Directive> directives) {
        for (String paramName : methodRepresentation.getParameters().keySet()) {
            boolean found = directives.stream().anyMatch(directive -> directive.getParameterName().equals(paramName));
            if (!found) {
                throw new IllegalArgumentException("Missing directive for parameter: " + paramName + " in method: " + methodRepresentation.getMethodName());
            }
        }
    }
}
