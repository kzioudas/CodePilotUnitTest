package codepilotunittest.directives;

/**
 * Interface representing a directive in a test case.
 */
public interface Directive {
    String getParameterName(); // Method to get the parameter name
    String getDirectiveType(); // Method to get the type of directive
    boolean validate(Object value); // Method to validate a value against the directive

}
