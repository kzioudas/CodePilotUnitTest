package codepilotunittest.directives;

import codepilotunittest.directives.Directive;


public class DirectiveFactory {

    /**
     * Factory method to create a directive based on a string.
     *
     * @param parameterName the name of the parameter
     * @param directiveString the string representation of the directive (e.g., "null", "range(0,7)", etc.)
     * @return a Directive object representing the directive
     */
    public static Directive createDirective(String parameterName, String directiveString, String expected) {
        if (directiveString.equalsIgnoreCase("null")) {
            return new NullDirective(parameterName);
        } else if (directiveString.equalsIgnoreCase("not null")) {
            return new NotNullDirective(parameterName);
        } else if (directiveString.matches("range\\((\\d+)\\-(\\d+)\\)")) {
            String[] rangeParts = directiveString.substring(6, directiveString.length() - 1).split("-");
            int min = Integer.parseInt(rangeParts[0].trim());
            int max = Integer.parseInt(rangeParts[1].trim());
            return new RangeDirective(parameterName, min, max);
        } else if (directiveString.matches("notInRange\\((\\d+)\\-(\\d+)\\)") ) {
            String[] rangeParts = directiveString.substring(11, directiveString.length() - 1).split("-");
            int min = Integer.parseInt(rangeParts[0].trim());
            int max = Integer.parseInt(rangeParts[1].trim());
            return new NotInRangeDirective(parameterName, min, max);
        } else {
            // Handle simple value directives, assuming they are like "par value 5"
            return new SimpleValueDirective(parameterName, directiveString.trim());
        }
    }
}
