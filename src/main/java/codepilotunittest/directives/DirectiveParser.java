package codepilotunittest.directives;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser for directive strings in the format "param: condition".
 */
public class DirectiveParser {

    /**
     * Parses the directive string from the CSV into a list of Directive objects.
     *
     * @param directiveString the string containing the directives
     * @return a list of parsed directives
     */
    public List<Directive> parse(String directiveString) {
        List<Directive> directives = new ArrayList<>();

        // Split by semicolon to get individual directives
        String[] directiveParts = directiveString.split(";");

        for (String part : directiveParts) {
            // Remove extra spaces around paramName and condition
            String cleanedPart = part.replaceAll("\\s*:\\s*:\\s*", ":").trim(); // Clean spaces around ':'

            // Split each directive by ':'
            String[] directiveElements = cleanedPart.split(":");
            if (directiveElements.length == 3) {
                String paramName = directiveElements[0].trim();
                String condition = directiveElements[1].trim();
                String expected = directiveElements[2].trim();
                directives.add(DirectiveFactory.createDirective(paramName, condition, expected));
            }else if (directiveElements.length == 2) {
                String paramName = directiveElements[0].trim();
                String condition = directiveElements[1].trim();
                directives.add(DirectiveFactory.createDirective(paramName, condition, null));
            }
        }

        return directives;
    }
}
