package codepilotunittest.directives;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Directive for a simple value (e.g., "param value 5").
 */
public class SimpleValueDirective implements Directive {

    private final Map<String, String> parameters; // Key: Parameter name, Value: Input value
    private final String expectedResult;          // Expected result (if any)
    private final String expectedBehavior;        // Expected behavior (e.g., true, exception type)

    /**
     * Constructs a SimpleValueDirective with the given parameters, result, and behavior.
     *
     * @param parameters       The parameters for the directive.
     * @param expectedResult   The expected result of the directive.
     * @param expectedBehavior The expected behavior of the directive.
     */
    public SimpleValueDirective(Map<String, String> parameters, String expectedResult, String expectedBehavior) {
        this.parameters = parameters;
        this.expectedResult = expectedResult;
        this.expectedBehavior = expectedBehavior;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String getParameterName(int index) {
        return parameters.keySet().toArray()[index].toString();
    }

    @Override
    public String getDirectiveType() {
        return "value";
    }

    @Override
    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String getExpectedBehavior() {
        return expectedBehavior;
    }
    //ToDo FIX THIS
    @Override
    public String generateAssertion() {
        StringBuilder assertions = new StringBuilder();
        Set<String> generatedAssertions = new HashSet<>();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            // Adjust based on the type of value
            String formattedValue = paramValue.matches("^\\d+(\\.\\d+)?$") ? paramValue : "\"" + paramValue + "\"";

            // Handle boolean expected result
            String assertion = "";
            if ("true".equalsIgnoreCase(expectedResult) || "false".equalsIgnoreCase(expectedResult)) {
                boolean expectedValue = Boolean.parseBoolean(expectedResult);
                assertion = String.format(
                        "assert%s(result, \"Expected %s to be %s\");%n",
                        expectedValue ? "True" : "False",
                        paramName,
                        expectedResult
                );
            } else {
                // Default case for other types
                assertion = String.format(
                        "assertEquals(result, %s, \"Expected result to equal %s\");%n"
                        , expectedResult, expectedResult
                );
            }

            // Check for duplicate assertions
            if (!generatedAssertions.contains(assertion)) {
                assertions.append(assertion);
                generatedAssertions.add(assertion);
            }
        }

        return assertions.toString();
    }

    @Override
    public String getParameterValue(String key) {
        return parameters.get(key).toString();
    }

    @Override
    public boolean validate(Object value) {
        return parameters.containsValue(value.toString());
    }

    @Override
    public String toString() {
        return "SimpleValueDirective{" +
                "parameters=" + parameters +
                ", expectedResult='" + expectedResult + '\'' +
                ", expectedBehavior='" + expectedBehavior + '\'' +
                '}';
    }
}
