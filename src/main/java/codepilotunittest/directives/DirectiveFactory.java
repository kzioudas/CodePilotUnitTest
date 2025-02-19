package codepilotunittest.directives;

import java.util.Map;

public class DirectiveFactory {

    /**
     * Creates a Directive with the given parameters, expected result, and behavior.
     *
     * @param parameters          A map of parameter names and values for the method.
     * @param expectedResult      The expected result of the method call.
     * @param expectedBehavior    The expected behavior (e.g., exception type).
     * @param constructorParameters A map of parameter names and values for constructor initialization.
     * @return A Directive object.
     */
    public static Directive createDirective(Map<String, String> parameters, String expectedResult, String expectedBehavior, Map<String, String> constructorParameters) {
        Directive directive;

        // Handle null directives
        if (parameters.containsValue("null")) {
            directive = new NullDirective(parameters, expectedResult, expectedBehavior, constructorParameters);
        }
        // Handle range directives
        else if (expectedResult != null && expectedResult.startsWith("range(")) {
            String range = expectedResult.substring(6, expectedResult.length() - 1); // Extract range values
            String[] rangeParts = range.split("-");
            int min = Integer.parseInt(rangeParts[0]);
            int max = Integer.parseInt(rangeParts[1]);
            directive = new RangeDirective(parameters, min, max, expectedResult, expectedBehavior, constructorParameters);
        }
        // Handle exception directives
        else if (expectedBehavior != null && expectedBehavior.contains("Exception")) {
            directive = new ThrowsExceptionDirective(parameters, expectedResult, expectedBehavior, constructorParameters);
        }
        // Default to SimpleValueDirective
        else {
            directive = new SimpleValueDirective(parameters, expectedResult, expectedBehavior, constructorParameters);
        }

        return directive;
    }
}
