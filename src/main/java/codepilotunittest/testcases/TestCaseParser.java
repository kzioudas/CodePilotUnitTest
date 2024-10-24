package codepilotunittest.testcases;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.DirectiveParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser for reading test cases from a CSV file.
 */
public class TestCaseParser {

    private final DirectiveParser directiveParser;

    public TestCaseParser() {
        this.directiveParser = new DirectiveParser();
    }

    /**
     * Parses the test cases from a CSV file.
     *
     * @param filePath the path to the CSV file
     * @return a list of parsed TestCase objects
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<TestCase> parseTestCases(Path filePath) throws IOException {
        List<TestCase> testCases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Extract columns from CSV line
                String testName = parts[0].trim();
                String testType = parts[1].trim();
                String classToTest = parts[2].trim();
                String methodToTest = parts[3].trim();
                String directivePart = parts[4].trim();

                // Parse the directives using DirectiveParser
                List<Directive> directives = directiveParser.parse(directivePart);

                // Create and add the TestCase object
                TestCase testCase = TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);
                testCases.add(testCase);
            }
        }

        return testCases;
    }
}
