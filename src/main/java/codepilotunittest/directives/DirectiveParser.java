package codepilotunittest.directives;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectiveParser {

    /**
     * Parses a directive with a list of parameters.
     *
     * @param parametersArray  The array of parameters (e.g., ["a:2", "b:4"]).
     * @param expectedResult   The expected result of the method call.
     * @param expectedBehavior The expected behavior (e.g., true, exception type).
     * @return A Directive object.
     */
    public static Directive parseDirective(String[] parametersArray, String expectedResult, String expectedBehavior) {
        Directive directive;
        Map<String, String> parameters = new HashMap<>();

        for (String parameter : parametersArray) {
            String[] parts = parameter.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid parameter format: " + parameter);
            }

            String name = parts[0].trim();
            String value = parts[1].trim();
            parameters.put(name, value);

        }

        return  DirectiveFactory.createDirective(parameters, expectedResult, expectedBehavior);
    }
}
