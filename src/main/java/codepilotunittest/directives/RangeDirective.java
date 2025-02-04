package codepilotunittest.directives;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Directive that checks if a parameter's value is within a given range.
 */
public class RangeDirective implements Directive {

    protected final Map<String, String> parameters; // Key: Parameter name, Value: Parameter value
    protected final int min;
    protected final int max;
    protected final String expected;
    protected final String expectedBehavior;
    protected final Map<String, String> constructorParameters;

    /**
     * Constructs a RangeDirective with specified parameters and range constraints.
     *
     * @param parameters            The parameters for the directive.
     * @param min                   The minimum value of the range.
     * @param max                   The maximum value of the range.
     * @param expected              The expected behavior or result.
     * @param expectedBehavior
     * @param constructorParameters
     */
    public RangeDirective(Map<String, String> parameters, int min, int max, String expected, String expectedBehavior, Map<String, String> constructorParameters) {
        this.parameters = parameters;
        this.min = min;
        this.max = max;
        this.expected = expected;
        this.expectedBehavior = expectedBehavior;
        this.constructorParameters = constructorParameters;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
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
        return "range";
    }

    /**
     * @return
     */
    @Override
    public String getExpectedResult() {
        return  expected;
    }

    /**
     * @return
     */
    @Override
    public String getExpectedBehavior() {
        return  expectedBehavior;
    }

    @Override
    public String generateAssertion() {
        StringBuilder assertions = new StringBuilder();
        Set<String> generatedAssertions = new HashSet<>();  // To track duplicates

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String paramName = entry.getKey();

            // Format the assertion
            String assertion = String.format(
                    "assertTrue(result >= %d && result <= %d, \"Expected result to be within range [%d, %d]\");%n",
                    min, max, min, max
            );

            // Check for duplicates
            if (!generatedAssertions.contains(assertion)) {
                assertions.append(assertion);
                generatedAssertions.add(assertion);  // Add to set to avoid duplicates
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
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
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
        return "RangeDirective{" +
                "parameters=" + parameters +
                ", min=" + min +
                ", max=" + max +
                ", expected='" + expected + '\'' +
                '}';
    }
}
