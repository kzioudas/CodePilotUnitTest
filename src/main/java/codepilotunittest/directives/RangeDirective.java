package codepilotunittest.directives;

/**
 * Directive that checks if a value is within a given range.
 */
public class RangeDirective implements Directive {
    private final String parameterName;
    private final String inputValue;
    private final String expected;
    private final int min;
    private final int max;

    public RangeDirective(String parameterName, String inputValue, int min, int max,   String expected) {
        this.parameterName = parameterName;
        this.inputValue = inputValue;
        this.expected = expected;
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "range";
    }

    @Override
    public String generateAssertion() {
        return String.format(
                "assertTrue(result >= %d && result <= %d, \"Expected result to be within range [%d, %d]\");",
                 min, max,  min, max
        );
    }

    @Override
    public String getParameterValue() {
        return inputValue;
    }

    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
    }
}
