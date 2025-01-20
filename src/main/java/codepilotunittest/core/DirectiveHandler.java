package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.RangeDirective;


/**
 * Utility class for processing directives and handling parameter values.
 */
public class DirectiveHandler {

    /**
     * Finds the value for a parameter based on its directive.
     *
     * @param directive A  {@link Directive} objects to search through.
     * @param paramName  The name of the parameter whose value is being retrieved.
     * @return The formatted parameter value as a string, or {@code null} if no
     *         matching directive or parameter is found.
     */
    public static String findDirectiveValue(Directive directive, String paramName) {

        // Check if the directive contains the parameter
        if (directive.getParameters().containsKey(paramName)) {
            String paramValue = directive.getParameterValue(paramName);

            // Handle special cases for RangeDirective
            if (directive instanceof RangeDirective) {
                RangeDirective rangeDirective = (RangeDirective) directive;
                return rangeDirective.getParameterValue(paramName);
            }

            // Handle null values explicitly
            if (paramValue == null) {
                return "null";
            }

            // Format the value for its type
            return formatValueForType(paramValue);
        }

        // Return null if no matching directive is found
        return null;
    }

    /**
     * Formats a raw value for Java code (e.g., wraps strings in quotes).
     *
     * @param value The raw value from the directive.
     * @return The formatted value as a String.
     */
    private static String formatValueForType(String value) {
        if (value == null) {
            return "null";
        }
        if (value.matches("^\\d+(\\.\\d+)?$")) { // Numeric value
            return value;
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) { // Boolean
            return value.toLowerCase();
        }
        // Assume String for other cases
        return "\"" + value + "\"";
    }
}
