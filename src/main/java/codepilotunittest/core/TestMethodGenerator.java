package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.structure.TestMethod;
import codepilotunittest.testcases.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static codepilotunittest.core.DirectiveHandler.findDirectiveValue;

/**
 * Generates individual test methods for a given test case.
 */
public class TestMethodGenerator {
    private final ClassRepresentation classRepresentation;

    /**
     * Constructor to initialize the generator for a specific class representation.
     *
     * @param classRepresentation The class representation for the tests being generated.
     */
    public TestMethodGenerator(ClassRepresentation classRepresentation) {
        this.classRepresentation = classRepresentation;
    }

    /**
     * Generates a test method for a given test case.
     *
     * @param testCase The test case to generate a method for.
     * @return A TestMethod object representing the test method.
     */
    public TestMethod generate(TestCase testCase) {
        MethodRepresentation methodRepresentation = testCase.getMethodToTest();
        Directive directive = testCase.getDirective();

        // Skip methods with void return types
        if (methodRepresentation.getReturnType().equals("void")) {
            return null;
        }

        // Create test method name
        String methodName = "test" + methodRepresentation.getMethodName() + "_" + testCase.getTestType().name().toLowerCase();

        // Generate constructor initialization using directive's constructor parameters
        String initializationBlock = generateInitializationBlock(directive.getConstructorParameters());

        // Generate method call
        String methodCall = generateMethodCall(methodRepresentation, directive);

        // Create TestMethod instance with method call passed in the constructor
        TestMethod testMethod = new TestMethod(methodName, methodCall);

        // Add initialization block
        testMethod.addInitialization(initializationBlock);

        // Add parameter preparations
        for (Map.Entry<String, String> param : methodRepresentation.getParameters().entrySet()) {
            String paramName = param.getKey();
            String paramValue = findDirectiveValue(directive, paramName);

            if (paramValue != null) {
                testMethod.addParameterPreparation(param.getValue() + " " + paramName + " = " + paramValue + ";");
            }
        }

        // Add assertion
        testMethod.addAssertion(directive.generateAssertion());

        return testMethod;
    }


    /**
     * Generates the initialization block for the class under test.
     *
     * @param constructorParams The constructor parameters to use during initialization.
     * @return A string representing the initialization block.
     */
    private String generateInitializationBlock(Map<String, String> constructorParams) {
        StringBuilder initialization = new StringBuilder();
        initialization.append(classRepresentation.getClassName()).append(" instance = new ")
                .append(classRepresentation.getClassName()).append("(");

        // Add constructor parameters
        List<String> params = new ArrayList<>();
        for (String value : constructorParams.values()) {
            params.add(formatValue(value));
        }
        initialization.append(String.join(", ", params));

        initialization.append(");");
        return initialization.toString();
    }

    /**
     * Generates the method call statement for a given method and its corresponding directives.
     *
     * @param method     The {@link MethodRepresentation} representing the method to be called.
     * @param directive  The {@link Directive} containing the parameter values.
     * @return A string representing the complete method call statement.
     */
    private String generateMethodCall(MethodRepresentation method, Directive directive) {
        StringBuilder sb = new StringBuilder();

        // Add return type and method name
        if (!"void".equals(method.getReturnType())) {
            sb.append(method.getReturnType()).append(" result = ");
        }
        sb.append("instance.").append(method.getMethodName()).append("(");

        // Generate the parameters for the method call
        List<String> params = new ArrayList<>();
        for (String paramName : method.getParameters().keySet()) {
            String paramValue = findDirectiveValue(directive, paramName);
            if (paramValue != null) {
                params.add(formatValue(paramValue));
            }
        }

        // Join parameters with commas
        sb.append(String.join(", ", params));

        // Close the method call
        sb.append(");");

        return sb.toString();
    }

    /**
     * Formats a value correctly based on its type.
     * - Adds quotes for string values.
     * - Leaves numbers as-is.
     *
     * @param value The value to format.
     * @return A properly formatted value string.
     */
    private String formatValue(String value) {
        if (isString(value)) {
            return "\"" + value + "\"";
        }
        return value;
    }

    /**
     * Checks if a value should be treated as a string.
     *
     * @param value The value to check.
     * @return true if the value is a string, false otherwise.
     */
    private boolean isString(String value) {
        return value.matches("[a-zA-Z].*") || value.matches("\".*\"");
    }
}
