package codepilotunittest.core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import codepilotunittest.directives.RangeDirective;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;
import codepilotunittest.directives.Directive;

public class JUnitTestGenerator {

    private final ProjectRepresentation project;

    public JUnitTestGenerator(ProjectRepresentation project) {
        this.project = project;
    }

    public void generateTests(Map<String, List<TestCase>> testCasesByClass, Path outputDir) throws IOException, ClassNotFoundException {
        for (String className : testCasesByClass.keySet()) {
            List<TestCase> testCases = testCasesByClass.get(className);
            ClassRepresentation classRepresentation = project.findClass(className);
            generateTestClassFile(classRepresentation, testCases, outputDir);
        }
    }

    private void generateTestClassFile(ClassRepresentation classRepresentation, List<TestCase> testCases, Path outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();

        // Package and imports
        sb.append("import org.junit.jupiter.api.Test;\n")
                .append("import static org.junit.jupiter.api.Assertions.*;\n\n");

        // Class declaration
        sb.append("public class ").append(classRepresentation.getClassName()).append("Test {\n\n");

        // Generate test methods
        for (TestCase testCase : testCases) {
            sb.append(generateTestMethod(classRepresentation, testCase)).append("\n");
        }

        // End of class
        sb.append("}\n");

        // Write to file
        try (FileWriter writer = new FileWriter(outputDir.resolve(classRepresentation.getClassName() + "Test.java").toFile())) {
            writer.write(sb.toString());
        }
    }

    private String generateTestMethod(ClassRepresentation classRepresentation, TestCase testCase) {
        StringBuilder sb = new StringBuilder();

        MethodRepresentation methodRepresentation = testCase.getMethodToTest();
        List<Directive> directives = testCase.getDirectives();

        // Method annotation and signature
        sb.append("    @Test\n")
                .append("    void test").append(methodRepresentation.getMethodName())
                .append("_").append(testCase.getTestType().name().toLowerCase()).append("() {\n");

        // Instantiate the class
        sb.append("        // Instantiate the class\n");
        sb.append(generateClassInitialization(classRepresentation, directives)).append("\n\n");

        // Prepare method parameters
        sb.append("        // Prepare method parameters\n");
        for (Map.Entry<String, String> param : methodRepresentation.getParameters().entrySet()) {
            String paramName = param.getKey();
            String paramType = param.getValue();
            String paramValue = findDirectiveValue(directives, paramName);

            sb.append("        ").append(paramType).append(" ").append(paramName)
                    .append(" = ").append(paramValue).append(";\n");
        }

        // Call the method
        sb.append("\n        // Call the method\n");
        sb.append("        ").append(methodRepresentation.getReturnType()).append(" result = ")
                .append("instance.").append(methodRepresentation.getMethodName()).append("(");
        sb.append(String.join(", ", methodRepresentation.getParameters().keySet()));
        sb.append(");\n\n");

        // Generate assertions
        sb.append("        // Assertions\n");
        for (Directive directive : directives) {
            sb.append("        ").append(directive.generateAssertion()).append("\n");
        }

        // End of method
        sb.append("    }\n");
        return sb.toString();
    }

    private String generateClassInitialization(ClassRepresentation classRepresentation, List<Directive> directives) {
        StringBuilder sb = new StringBuilder();

        // Fetch constructor details
        MethodRepresentation constructor = classRepresentation.getConstructors().get(0); // Use the first constructor
        Map<String, String> constructorParams = constructor.getParameters();

        sb.append(classRepresentation.getClassName()).append(" instance = new ").append(classRepresentation.getClassName()).append("(");

        // Prepare constructor arguments
        int i = 0; // Index for handling commas
        int paramCount = constructorParams.size(); // Total number of parameters
        for (Map.Entry<String, String> param : constructorParams.entrySet()) {
            String paramName = param.getKey();
            String paramType = param.getValue();
            String paramValue = findDirectiveValue(directives, paramName);

            sb.append(paramValue);

            if (i < paramCount - 1) { // Add a comma if not the last parameter
                sb.append(", ");
            }
            i++; // Increment index
        }

        sb.append(");");
        return sb.toString();
    }

    private String findDirectiveValue(List<Directive> directives, String paramName) {
        for (Directive directive : directives) {
            if (directive.getParameterName().equals(paramName)) {
                if (directive instanceof RangeDirective) {
                    RangeDirective rangeDirective = (RangeDirective) directive;
                    int midValue = ((int) rangeDirective.getMin() + (int) rangeDirective.getMax()) / 2; // Use mid-range value
                    return String.valueOf(midValue);
                }
                if (directive.getParameterValue() == null) {
                    return "null";
                }

                return formatValueForType(directive.getParameterValue());
            }
        }
        throw new IllegalArgumentException("No directive found for parameter: " + paramName);
    }

    /**
     * Formats a value to be used in Java code (e.g., wraps strings in quotes).
     *
     * @param value The raw value from the directive.
     * @return The formatted value for Java code.
     */
    private String formatValueForType(String value) {
        if (value.matches("^-?\\d+(\\.\\d+)?$")) { // Numeric value
            return value;
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) { // Boolean
            return value.toLowerCase();
        } else { // Assume string
            return "\"" + value + "\"";
        }
    }
}
