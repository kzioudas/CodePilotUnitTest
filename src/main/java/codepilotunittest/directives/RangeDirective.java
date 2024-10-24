package codepilotunittest.directives;

/**
 * Directive that checks if a value is within a given range.
 */
public class RangeDirective implements Directive {

    private final String parameterName;
    private final int min;
    private final int max;

    public RangeDirective(String parameterName, int min, int max) {
        this.parameterName = parameterName;
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
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue >= min && intValue <= max;
        }
        return false;
    }
}

