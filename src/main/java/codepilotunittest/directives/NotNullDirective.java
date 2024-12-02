package codepilotunittest.directives;

/**
 * Directive that checks if a value is not null.
 */
public class NotNullDirective implements Directive {

    private final String parameterName;

    public NotNullDirective(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "not null";
    }

    /**
     * @return
     */
    @Override
    public String generateAssertion() {
        return String.format(
                "assertNotNull(%s, \"Expected %s to be not null\");",
                parameterName, parameterName
        );
    }


    @Override
    public boolean validate(Object value) {
        return value == "not null";
    }
}

