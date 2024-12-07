package codepilotunittest.directives;


public class DirectiveFactory {

    /**
     * Factory method to create a directive based on a string.
     *
     * @param paramName        Name of the parameter.
     * @param inputValue       Input value provided for the directive.
     * @param responseExpected The expected response from the method.
     * @param expected         The expected outcome of the test case.
     * @return a Directive object representing the directive.
     */
    public static Directive createDirective(String paramName, String inputValue, String responseExpected,String expected) {
        if (expected.equalsIgnoreCase("True")||expected.equalsIgnoreCase("False")) {
            if (inputValue.equalsIgnoreCase("NULL")) {
                return new NullDirective(paramName, inputValue, responseExpected, expected);
            } else if (responseExpected.matches("range\\((\\d+)\\-(\\d+)\\)")) {
                String[] rangeParts = responseExpected.substring(6, responseExpected.length() - 1).split("-");
                int min = Integer.parseInt(rangeParts[0].trim());
                int max = Integer.parseInt(rangeParts[1].trim());
                return new RangeDirective(paramName, inputValue, min, max, expected);
            } else {
                return new SimpleValueDirective(paramName, inputValue, responseExpected, expected);
            }
        } else if (expected.toLowerCase().contains("exception")) {
            return new ThrowsExceptionDirective(paramName, responseExpected, expected);
        }
        return null;
    }
}
