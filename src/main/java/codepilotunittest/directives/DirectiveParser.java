package codepilotunittest.directives;

//import java.util.List;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectiveParser {
    private String directiveString;

    public DirectiveParser(String directiveString) {
        this.directiveString = directiveString;
    }

    public Map<String, String> parse() {
        Map<String, String> parsedDirectives = new HashMap<>();

        // Split the directives by commas
        String[] directives = directiveString.split(",");

        for (String directive : directives) {
            // Split each directive by the colon ":"
            if (directive.contains(":")) {
                String[] parts = directive.split(":", 2);
                String directiveType = parts[0].trim();
                String directiveValue = parts[1].trim();

                // Add the directive type and value to the map
                parsedDirectives.put(directiveType, directiveValue);
            }
        }

        return parsedDirectives;
    }
}


