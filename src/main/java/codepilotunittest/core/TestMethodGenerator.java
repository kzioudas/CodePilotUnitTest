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

        // Create a TestMethod object
        String methodName = "test" + methodRepresentation.getMethodName() + "_" + testCase.getTestType().name().toLowerCase();
        String methodCall = generateMethodCall(methodRepresentation, directive);
        TestMethod testMethod = new TestMethod(methodName, methodCall);

        // Add initialization block
        testMethod.addInitialization(generateInitializationBlock());

        // Add parameter preparations
        for (Map.Entry<String, String> param : methodRepresentation.getParameters().entrySet()) {
            String paramName = param.getKey();
            String paramValue = findDirectiveValue(directive, paramName);

            if (paramValue != null) {
                testMethod.addParameterPreparation(param.getValue() + " " + paramName + " = " + paramValue + ";");
            }
        }


        testMethod.addAssertion(directive.generateAssertion());


        return testMethod;
    }

    /**
     * Generates the initialization block for the class under test.
     *
     * @return A string representing the initialization block.
     */
    private String generateInitializationBlock() {
        return classRepresentation.getClassName() + " instance = new " + classRepresentation.getClassName() + "();";
    }

    /**
     * Generates the method call statement for a given method and its corresponding directives.
     *
     * @param method     The {@link MethodRepresentation} representing the method to be called.
     * @param directives A list of {@link Directive} objects containing the parameter values.
     * @return A string representing the complete method call statement.
     */
    private String generateMethodCall(MethodRepresentation method, Directive directive) {
        // Start building the method call
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
            if (paramValue != null) { // Skip parameters without matching directives
                params.add(paramValue);
            }
        }

        // Join parameters with commas
        sb.append(String.join(", ", params));

        // Close the method call
        sb.append(");");

        return sb.toString();
    }
}
