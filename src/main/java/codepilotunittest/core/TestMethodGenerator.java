package codepilotunittest.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.testcases.TestCase;

/**
 * Generates test methods for a given test case.
 */
public class TestMethodGenerator {

    private final ClassRepresentation classRepresentation;

    public TestMethodGenerator(ClassRepresentation classRepresentation) {
        this.classRepresentation = classRepresentation;
    }

    /**
     * Generates a single test method for the given test case.
     *
     * @param testCase The test case details.
     * @return The generated test method code as a String.
     */
    public String generate(TestCase testCase) {
        StringBuilder sb = new StringBuilder();

        MethodRepresentation methodRepresentation = testCase.getMethodToTest();
        List<Directive> directives = testCase.getDirectives();

        // Method annotation and signature
        sb.append("    @Test\n")
                .append("    void test").append(methodRepresentation.getMethodName())
                .append("_").append(testCase.getTestType().name().toLowerCase()).append("() {\n");

        // Instantiate the class
        sb.append("        // Instantiate the class\n");
        ClassInitializationHandler classInitHandler = new ClassInitializationHandler(classRepresentation, directives);
        sb.append(classInitHandler.generateInitialization()).append("\n\n");

        // Prepare method parameters, skipping those without directives
        sb.append("        // Prepare method parameters\n");
        List<String> parameterNames = new ArrayList<>();
        for (Map.Entry<String, String> param : methodRepresentation.getParameters().entrySet()) {
            String paramName = param.getKey();
            String paramType = param.getValue();
            String paramValue = DirectiveHandler.findDirectiveValue(directives, paramName);

            if (paramValue != null) {
                sb.append("        ").append(paramType).append(" ").append(paramName)
                        .append(" = ").append(paramValue).append(";\n");
                parameterNames.add(paramName); // Add only parameters with directives
            }
        }

        // Call the method
        sb.append("\n        // Call the method\n");
        sb.append("        ").append(methodRepresentation.getReturnType()).append(" result = ")
                .append("instance.").append(methodRepresentation.getMethodName()).append("(");
        sb.append(String.join(", ", parameterNames)); // Only use parameters with directives
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
}
