package codepilotunittest.directives;

/**
 * Directive that checks if a value is null.
 */
public class NullDirective implements Directive {

    private final String parameterName;

    public NullDirective(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "null";
    }

    @Override
    public boolean validate(Object value) {
        return value == "null";
    }
}
