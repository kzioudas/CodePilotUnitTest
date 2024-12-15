package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.structure.TestMethod;
import codepilotunittest.testcases.TestCase;

import java.util.List;
import java.util.Map;

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
        List<Directive> directives = testCase.getDirectives();

        // Skip methods with void return types
        if (methodRepresentation.getReturnType().equals("void")) {
            return null;
        }

        // Create a TestMethod object
        String methodName = "test" + methodRepresentation.getMethodName() + "_" + testCase.getTestType().name().toLowerCase();
        String methodCall = generateMethodCall(methodRepresentation, directives);
        TestMethod testMethod = new TestMethod(methodName, methodCall);

        // Add initialization block
        testMethod.addInitialization(generateInitializationBlock());

        // Add parameter preparations
        for (Map.Entry<String, String> param : methodRepresentation.getParameters().entrySet()) {
            String paramName = param.getKey();
            String paramValue = DirectiveHandler.findDirectiveValue(directives, paramName);

            if (paramValue != null) {
                testMethod.addParameterPreparation(param.getValue() + " " + paramName + " = " + paramValue + ";");
            }
        }

        // Add assertions
        for (Directive directive : directives) {
            testMethod.addAssertion(directive.generateAssertion());
        }

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
     * Generates the method call string for the test method.
     *
     * @param method     The method being tested.
     * @param directives List of directives for the test case.
     * @return A string representing the method call.
     */
    private String generateMethodCall(MethodRepresentation method, List<Directive> directives) {
        return method.getReturnType() + " result = instance." + method.getMethodName() + "(/*params*/);";
    }
}
