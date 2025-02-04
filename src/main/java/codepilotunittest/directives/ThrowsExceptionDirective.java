package codepilotunittest.directives;

import java.util.Map;

/**
 * Directive that checks if a method throws a specific exception.
 */
public class ThrowsExceptionDirective implements Directive {
    private final Map<String, String> parameters; // Key: Parameter name, Value: Input value
    private final String expectedException;
    private final String expectedBehavior;
    private final Map<String, String> constructorParameters;

    /**
     * Constructs a ThrowsExceptionDirective with the given parameters and expected exception.
     *
     * @param parameters            The parameters for the directive.
     * @param expectedException     The expected exception type.
     * @param expectedBehavior
     * @param constructorParameters
     */
    public ThrowsExceptionDirective(Map<String, String> parameters, String expectedException, String expectedBehavior, Map<String, String> constructorParameters) {
        this.parameters = parameters;
        this.expectedException = expectedException;
        this.expectedBehavior = expectedBehavior;
        this.constructorParameters = constructorParameters;
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
        return "throwsException";
    }

    @Override
    public String getExpectedResult() {
        return expectedException;
    }

    @Override
    public String getExpectedBehavior() {
        return expectedBehavior ;
    }

    @Override
    public String generateAssertion() {
        StringBuilder params = new StringBuilder();
        parameters.forEach((key, value) -> {
            String formattedValue = value.matches("^\\d+(\\.\\d+)?$") ? value : "\"" + value + "\"";
            params.append(formattedValue).append(", ");
        });

        // Remove trailing comma and space
        if (params.length() > 0) {
            params.setLength(params.length() - 2);
        }

        return String.format(
                "assertThrows(%s.class, () -> instance.%s(%s), \"Expected method to throw %s\");",
                expectedException, "methodName", params, expectedException
        );
    }

    @Override
    public String getParameterValue(String key) {
        return parameters.get(key).toString();
    }

    @Override
    public boolean validate(Object value) {
        return false; // Validation happens through exception checking in the test case.
    }

    /**
     * @return a map containing parameter names and values for constructor initialization
     */
    @Override
    public Map<String, String> getConstructorParameters() {
        return constructorParameters;
    }

    @Override
    public String toString() {
        return "ThrowsExceptionDirective{" +
                "parameters=" + parameters +
                ", expectedException='" + expectedException + '\'' +
                '}';
    }
}
