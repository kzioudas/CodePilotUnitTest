package codepilotunittest.directives;

/**
 * Directive that checks if a value is within a given range.
 */
public class RangeDirective implements Directive {
    private final String parameterName;
    private final String responceExpected;
    private final String expected;
    private final int min;
    private final int max;

    public RangeDirective(String parameterName, int min, int max , String responceExpected, String expected) {
        this.parameterName = parameterName;
        this.responceExpected = responceExpected;
        this.expected = expected;
        this.min = min;
        this.max = max;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "range(" + min + "," + max + ")";
    }

    public Object getMax() {
        return this.max;
    }
    
    public Object getMin() {
        return this.min;
    }
    @Override
    public String generateAssertion() {
        return String.format(
                "assertTrue(%s >= %d && %s <= %d, \"Expected %s to be within range [%d, %d]\");",
                parameterName, min, parameterName, max, parameterName, min, max
        );
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

