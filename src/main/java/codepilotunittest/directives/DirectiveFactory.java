package codepilotunittest.directives;


public class DirectiveFactory {

    /**
     * Factory method to create a directive based on a string.
     *
     * @param paramName
     * @param inputValue
     * @param responceExpected
     * @param expected
     * @return a Directive object representing the directive
     */
    public static Directive createDirective(String paramName, String inputValue, String responceExpected,String expected) {
        if (expected.contains("True")||expected.contains("false")) {
            if (inputValue.equalsIgnoreCase("NULL")) {
                return new NullDirective(paramName, inputValue, responceExpected, expected);
            } else if (inputValue.matches("range\\((\\d+)\\-(\\d+)\\)")) {
                String[] rangeParts = inputValue.substring(6, inputValue.length() - 1).split("-");
                int min = Integer.parseInt(rangeParts[0].trim());
                int max = Integer.parseInt(rangeParts[1].trim());
                return new RangeDirective(paramName, min, max, responceExpected, expected);
            } else {
                return new SimpleValueDirective(paramName, inputValue, responceExpected, expected);
            }
        } else if(expected.toLowerCase().contains("exception")) {
            if (inputValue.equalsIgnoreCase("NULL")) {
                return new NullDirective(paramName, inputValue, responceExpected, expected);
            } else if (inputValue.matches("range\\((\\d+)\\-(\\d+)\\)")) {
                String[] rangeParts = inputValue.substring(6, inputValue.length() - 1).split("-");
                int min = Integer.parseInt(rangeParts[0].trim());
                int max = Integer.parseInt(rangeParts[1].trim());
                return new RangeDirective(paramName, min, max, responceExpected, expected);
            } else {
                return new SimpleValueDirective(paramName, inputValue, responceExpected, expected);
            }
        }
        return null;
    }
}
