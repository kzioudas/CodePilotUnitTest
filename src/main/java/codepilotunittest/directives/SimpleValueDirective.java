package codepilotunittest.directives;


/**
 * Directive for a simple value (e.g., "par value 5").
 */
public class SimpleValueDirective implements Directive {

    private final String parameterName;
    private final Object parameterValue;

    public SimpleValueDirective(String parameterName, Object parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "value";
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    @Override
    public boolean validate(Object value) {
        return value.equals(parameterValue);
    }
}

