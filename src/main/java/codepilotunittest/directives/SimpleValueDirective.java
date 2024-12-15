package codepilotunittest.directives;


/**
 * Directive for a simple value (e.g., "par value 5").
 */
public class SimpleValueDirective implements Directive {


    private final String parameterName;
    private final String inputValue;
    private final String responceExpected;
    private final String expected;

    public SimpleValueDirective(String parameterName, String inputValue, String responceExpected, String expected) {
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
        return "value";
    }

    //todo fix that
    /**
     * @return
     */
    //ToDo for other types (String,int etc)
    public String generateAssertion() {
        // Adjust based on the expected return type (e.g., quotes for String)
        String formattedValue = inputValue.matches("^\\d+(\\.\\d+)?$") ? inputValue : "\"" + inputValue + "\"";

        // Handle boolean return type
        if ("true".equalsIgnoreCase(responceExpected) || "false".equalsIgnoreCase(responceExpected)) {
            boolean expectedValue = Boolean.parseBoolean(responceExpected);
            return String.format(
                    "assert%s(result, \"Expected %s to be %s\");",
                    expectedValue ? "True" : "False",
                    parameterName,
                    parameterName,
                    expected
            );
        }

        // Default case for other types
        return String.format(
                "assertEquals(%s, %s, \"Expected %s to equal %s\");",
                formattedValue, parameterName, parameterName, formattedValue
        );
    }


    public String getParameterValue() {
        return inputValue;
    }


    public String toString(){
        return "SimpleValueDirective [parameterName=" + parameterName + ", parameterValue=" + inputValue + "]" + generateAssertion();
    }
    @Override
    public boolean validate(Object value) {
        return value.equals(inputValue);
    }
}

