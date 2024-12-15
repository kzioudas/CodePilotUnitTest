package codepilotunittest.directives;

public class ThrowsExceptionDirective implements Directive {
    private final String parameterName;
    private final String expectedException;

    public ThrowsExceptionDirective(String parameterName, String responseExpected, String expected) {
        this.parameterName = parameterName;
        this.expectedException = expected;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "throwsException";
    }

    @Override
    public String generateAssertion() {
        return String.format(
                "assertThrows(%s.class, () -> instance.%s(%s), \"Expected %s to throw %s\");",
                expectedException, parameterName, parameterName, parameterName, expectedException
        );
    }

    /**
     * @return
     * TODO FIX
     */
    @Override
    public String getParameterValue() {
        return "";
    }

    @Override
    public boolean validate(Object value) {
        return false; // Validation happens through exception checking in the test case.
    }
}
