package codepilotunittest.directives;

import java.util.HashMap;
import java.util.Map;

public class DirectiveParser {

    /**
     * Parses a directive with a list of parameters and constructor parameters.
     *
     * @param parameters  The method parameters
     * @param expectedResult    The expected result of the method call.
     * @param expectedBehavior  The expected behavior (e.g., true, exception type, "constructor").
     * @param constructorParams The constructor parameters
     * @return A Directive object with both method and constructor parameters.
     */
    public static Directive parseDirective(String parameters, String expectedResult, String expectedBehavior, String constructorParams) {
        Map<String, String> methodParameters = new HashMap<>();
        Map<String, String> constructorParameters = new HashMap<>();
        String[] parametersArray = parameters.split(";");
        String[] constructorParametersArray = constructorParams.split(";");
        // Parse method parameters
        if (!parameters.equals("-")) {
            for (String parameter : parametersArray) {
                String[] parts = parameter.split(":");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid parameter format: " + parameter);
                }
                methodParameters.put(parts[0].trim(), parts[1].trim());
            }
        }

        if ("<directives>".equalsIgnoreCase(constructorParams)){
            // Reuse directive parameters if <directives> is specified
            constructorParameters.putAll(methodParameters);
        }else if(!constructorParams.equals("-")) {
            for (String parameter : constructorParametersArray) {
                String[] parts = parameter.split(":");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid constructor parameter format: " + parameter);
                }
                constructorParameters.put(parts[0].trim(), parts[1].trim());
            }
        }

        // Create the directive with both method and constructor parameters
        Directive directive = DirectiveFactory.createDirective(methodParameters, expectedResult, expectedBehavior,constructorParameters);

        return directive;
    }
}
