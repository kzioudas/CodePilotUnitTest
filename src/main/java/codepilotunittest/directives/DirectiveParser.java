package codepilotunittest.directives;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser for directive strings in test case definitions.
 */
public class DirectiveParser {

    /**
     * Parses a directive string into a list of directives.
     *
     * @param directiveString The directive string.
     * @return a list of directives.
     */
    public List<Directive> parse(String directiveString) {
        List<Directive> directives = new ArrayList<>();

        // Split directives by semicolon
        String[] directiveParts = directiveString.split(";");

        for (String part : directiveParts) {
            String[] directiveElements = part.split(":");
            if (directiveElements.length < 4) {
                throw new IllegalArgumentException("Invalid directive format: " + part);
            }
            String paramName = directiveElements[0].trim();
            String inputValue = directiveElements[1].trim();
            String responseExpected = directiveElements[2].trim();
            String expected = directiveElements[3].trim();
            directives.add(DirectiveFactory.createDirective(paramName, inputValue, responseExpected, expected));
        }

        return directives;
    }
}
