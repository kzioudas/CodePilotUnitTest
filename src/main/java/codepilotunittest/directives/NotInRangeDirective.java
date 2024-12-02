package codepilotunittest.directives;


/**
 * Directive that checks if a value is outside a given range.
 */
public class NotInRangeDirective implements Directive {

    private final String parameterName;
    private final int min;
    private final int max;

    public NotInRangeDirective(String parameterName, int min, int max) {
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
        return "notInRange(" + min + "," + max + ")";
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
                "assertFalse(%s >= %d && %s <= %d, \"Expected %s to be outside range [%d, %d]\");",
                parameterName, min, parameterName, max, parameterName, min, max
        );
    }

    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return intValue < min || intValue > max;
        }
        return false;
    }
}
