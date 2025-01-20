package codepilotunittest.directives;

/**
 * Represents a single parameter in a directive.
 */
public class DirectiveParameter {
    private final String name;
    private final String value;

    public DirectiveParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }
}
