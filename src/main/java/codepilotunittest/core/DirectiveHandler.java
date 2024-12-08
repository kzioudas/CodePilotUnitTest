package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.RangeDirective;

import java.util.List;

/**
 * Utility class for processing directives and handling parameter values.
 */
public class DirectiveHandler {

    /**
     * Finds the value for a parameter based on its directive.
     *
     * @param directives The list of directives associated with the test case.
     * @param paramName  The name of the parameter to find a value for.
     * @return The value of the parameter as a String, or null if no directive is found.
     */
    public static String findDirectiveValue(List<Directive> directives, String paramName) {
        for (Directive directive : directives) {
            if (directive.getParameterName().equals(paramName)) {
                if (directive instanceof RangeDirective) {
                    RangeDirective rangeDirective = (RangeDirective) directive;
                    int midValue = (rangeDirective.getMin() + rangeDirective.getMax()) / 2; // Midpoint
                    return String.valueOf(midValue);
                }
                if (directive.getParameterValue() == null) {
                    return "null";
                }

                return formatValueForType(directive.getParameterValue());
            }
        }
        // Return null if no directive is found
        return null;
    }

    /**
     * Formats a raw value for Java code (e.g., wraps strings in quotes).
     *
     * @param value The raw value from the directive.
     * @return The formatted value as a String.
     */
    public static String formatValueForType(String value) {
        if (value.matches("^-?\\d+(\\.\\d+)?$")) { // Numeric value
            return value;
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) { // Boolean
            return value.toLowerCase();
        } else { // Assume string
            return "\"" + value + "\"";
        }
    }
}
