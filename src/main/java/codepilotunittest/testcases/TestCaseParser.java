package codepilotunittest.testcases;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TestCaseParser {

    public List<TestCase> parseTestCases(Path filePath) throws IOException {
        List<TestCase> testCases = new ArrayList<>();
        try (BufferedReader reader = getBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                TestCase testCase = parseLineToTestCase(line);
                if (testCase != null) {
                    testCases.add(testCase);
                }
            }
        }
        return testCases;
    }

    protected BufferedReader getBufferedReader(Path filePath) throws IOException {
        return Files.newBufferedReader(filePath);
    }

    private TestCase parseLineToTestCase(String line) {
        // Assuming file structure: "testname"|"testtype"|"classtotest"|methodToTest|"directives" {dir1, dir2, dir3}
        String[] parts = line.split("\\|");
        if (parts.length < 5) {
            throw new ArrayIndexOutOfBoundsException("Invalid test case format");
        }

        String testName = parts[0].replace("\"", "").trim();
        String testType = parts[1].replace("\"", "").trim();
        String classToTest = parts[2].replace("\"", "").trim();
        String methodToTest = parts[3].replace("\"", "").trim();

        // Extracting and cleaning up directives
        String[] directivesArray = parts[4]
                .replace("\"directives\"", "")   // Removing "directives"
                .replace("{", "")                // Removing curly braces
                .replace("}", "")                // Removing curly braces
                .split(",");                     // Splitting by commas

        // Trimming spaces around each directive
        List<String> directives = new ArrayList<>();
        for (String directive : directivesArray) {
            directives.add(directive.trim());
        }

        return TestCaseFactory.createTestCase(testName, testType, classToTest, methodToTest, directives);
    }
}