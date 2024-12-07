package codepilotunittest.directives;

/**
 * Directive that checks if a value is null.
 */
public class NullDirective implements Directive {

    private final String parameterName;
    private final String inputValue;
    private final String responceExpected;
    private final String expected;

    public NullDirective(String parameterName, String inputValue, String responceExpected, String expected) {
        this.parameterName = parameterName;
        this.inputValue = inputValue;
        this.responceExpected = responceExpected;
        this.expected = expected;
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
    public String generateAssertion() {
        return String.format(
                "assertNull(%s, \"Expected %s to be null\");",
                parameterName, parameterName
        );
    }

    /**
     * @return
     */
    @Override
    public String getParameterValue() {
        return null;
    }

    @Override
    public boolean validate(Object value) {
        return value == "null";
    }
}
