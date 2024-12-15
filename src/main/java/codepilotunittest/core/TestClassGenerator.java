package codepilotunittest.core;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.structure.TestClass;
import codepilotunittest.structure.TestMethod;
import codepilotunittest.testcases.TestCase;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Generates a JUnit test class for a given class and its test cases.
 */
public class TestClassGenerator {
    private final ClassRepresentation classRepresentation;
    private final List<TestCase> testCases;
    private final Path outputDir;

    /**
     * Constructor to initialize the generator.
     *
     * @param classRepresentation The representation of the class being tested.
     * @param testCases           List of test cases for the class.
     * @param outputDir           Directory where the generated test file will be written.
     */
    public TestClassGenerator(ClassRepresentation classRepresentation, List<TestCase> testCases, Path outputDir) {
        this.classRepresentation = classRepresentation;
        this.testCases = testCases;
        this.outputDir = outputDir;
    }

    /**
     * Generates the test class and writes it to a file.
     *
     * @throws IOException If file writing fails.
     */
    public void generate() throws IOException {
        // Create a TestClass object
        TestClass testClass = new TestClass(classRepresentation.getClassName());
        TestMethodGenerator methodGenerator = new TestMethodGenerator(classRepresentation);

        // Generate and add TestMethods to TestClass
        for (TestCase testCase : testCases) {
            TestMethod testMethod = methodGenerator.generate(testCase);
            if (testMethod != null) {
                testClass.addMethod(testMethod);
            }
        }

        // Write the TestClass to a file
        try (FileWriter writer = new FileWriter(outputDir.resolve(classRepresentation.getClassName() + "Test.java").toFile())) {
            writer.write(testClass.render());
        }
    }
}
