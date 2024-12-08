package codepilotunittest.core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.testcases.TestCase;

/**
 * Generates a JUnit test class for a specific class representation.
 */
public class TestClassGenerator {

    private final ClassRepresentation classRepresentation;
    private final List<TestCase> testCases;
    private final Path outputDir;

    /**
     * Constructor for TestClassGenerator.
     *
     * @param classRepresentation The class representation to generate tests for.
     * @param testCases The list of test cases for the class.
     * @param outputDir The output directory for the test file.
     */
    public TestClassGenerator(ClassRepresentation classRepresentation, List<TestCase> testCases, Path outputDir) {
        this.classRepresentation = classRepresentation;
        this.testCases = testCases;
        this.outputDir = outputDir;
    }

    /**
     * Generates the test class and writes it to the output directory.
     *
     * @throws IOException If there is an error writing the test file.
     */
    public void generate() throws IOException {
        StringBuilder sb = new StringBuilder();

        // Package and imports
        sb.append("import org.junit.jupiter.api.Test;\n")
                .append("import static org.junit.jupiter.api.Assertions.*;\n\n");

        // Class declaration
        sb.append("public class ").append(classRepresentation.getClassName()).append("Test {\n\n");

        // Generate test methods
        TestMethodGenerator methodGenerator = new TestMethodGenerator(classRepresentation);
        for (TestCase testCase : testCases) {
            sb.append(methodGenerator.generate(testCase)).append("\n");
        }

        // End of class
        sb.append("}\n");

        // Write to file
        try (FileWriter writer = new FileWriter(outputDir.resolve(classRepresentation.getClassName() + "Test.java").toFile())) {
            writer.write(sb.toString());
        }
    }
}
