package codepilotunittest.directives;

import java.util.Map;

public class DirectiveFactory {

    /**
     * Creates a Directive with the given parameters, expected result, and behavior.
     *
     * @param parameters       A map of parameter names and values.
     * @param expectedResult   The expected result of the method call.
     * @param expectedBehavior The expected behavior (e.g., exception type).
     * @return A Directive object.
     */
    public static Directive createDirective(Map<String, String> parameters, String expectedResult, String expectedBehavior) {
        // Handle null directives
        if (parameters.containsValue("null")) {
            return new NullDirective(parameters, expectedResult, expectedBehavior);
        }

        // Handle range directives
        if (expectedResult != null && expectedResult.startsWith("range(")) {
            String range = expectedResult.substring(6, expectedResult.length() - 1); // Extract range values
            String[] rangeParts = range.split("-");
            int min = Integer.parseInt(rangeParts[0]);
            int max = Integer.parseInt(rangeParts[1]);
            return new RangeDirective(parameters, min, max, expectedResult, expectedBehavior);
        }

        // Handle exception directives
        if (expectedBehavior != null && expectedBehavior.contains("Exception")) {
            return new ThrowsExceptionDirective(parameters, expectedResult, expectedBehavior);
        }

        // Default to SimpleValueDirective
        return new SimpleValueDirective(parameters, expectedResult, expectedBehavior);
    }
}
