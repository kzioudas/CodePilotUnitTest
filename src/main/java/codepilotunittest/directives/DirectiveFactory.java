package codepilotunittest.directives;

/**
 * Factory class for creating directives based on input strings.
 */
public class DirectiveFactory {

    /**
     * Factory method to create a directive.
     *
     * @param paramName        Name of the parameter.
     * @param inputValue       Input value provided for the directive.
     * @param responseExpected The expected response.
     * @param expected         The expected result.
     * @return a Directive object.
     */
    public static Directive createDirective(String paramName, String inputValue, String responseExpected, String expected) {
        if (expected.equalsIgnoreCase("true") || expected.equalsIgnoreCase("false")) {
            if ("null".equalsIgnoreCase(inputValue)) {
                return new NullDirective(paramName, inputValue, responseExpected, expected);
            } else if (responseExpected.matches("range\\((\\d+)-(\\d+)\\)")) {
                String[] rangeParts = responseExpected.substring(6, responseExpected.length() - 1).split("-");
                int min = Integer.parseInt(rangeParts[0].trim());
                int max = Integer.parseInt(rangeParts[1].trim());
                return new RangeDirective(paramName, inputValue, min, max,  expected);
            } else {
                return new SimpleValueDirective(paramName, inputValue, responseExpected, expected);
            }
        } else if (expected.toLowerCase().contains("exception")) {
            return new ThrowsExceptionDirective(paramName, responseExpected, expected);
        }
        return null; // Handle unknown directive cases
    }
}
