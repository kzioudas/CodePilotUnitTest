package codepilotunittest.directives;

import java.util.Map;

/**
 * Directive that checks if specified parameters are null.
 */
public class NullDirective implements Directive {

    protected final Map<String, String> parameters; // Key: Parameter name, Value: Parameter value
    protected final String expectedResult;
    protected final String expectedBehavior;

    /**
     * Constructs a NullDirective with specified parameters, expected result, and behavior.
     *
     * @param parameters       The parameters for the directive.
     * @param expectedResult   The expected result of the method call.
     * @param expectedBehavior The expected behavior of the method call.
     */
    public NullDirective(Map<String, String> parameters, String expectedResult, String expectedBehavior) {
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
        return "null";
    }

    @Override
    public String getExpectedResult() {
        return expectedResult;
    }

    @Override
    public String getExpectedBehavior() {
        return expectedBehavior;
    }

    @Override
    public String generateAssertion() {
        StringBuilder assertions = new StringBuilder();
        for (String paramName : parameters.keySet()) {
            assertions.append(String.format(
                    "assertNull(%s, \"Expected %s to be null\");%n",
                    paramName, paramName
            ));
        }
        return assertions.toString();
    }

    @Override
    public String getParameterValue(String key) {
        // NullDirective implies parameters should have null values
        return "null";
    }

    @Override
    public boolean validate(Object value) {
        return value == null;
    }

    @Override
    public String toString() {
        return "NullDirective{" +
                "parameters=" + parameters +
                ", expectedResult='" + expectedResult + '\'' +
                ", expectedBehavior='" + expectedBehavior + '\'' +
                '}';
    }
}
