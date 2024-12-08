package codepilotunittest.directives;

/**
 * Interface representing a directive in a test case.
 */
public interface Directive {
    /**
     * Gets the parameter name associated with the directive.
     *
     * @return the parameter name
     */
    String getParameterName();

    /**
     * Gets the type of the directive.
     *
     * @return the directive type
     */
    String getDirectiveType();

    /**
     * Generates the assertion code for the directive.
     *
     * @return the assertion code as a string
     */
    String generateAssertion();

    /**
     * Gets the parameter value associated with the directive.
     *
     * @return the parameter value
     */
    String getParameterValue();

    /**
     * Validates a value against the directive's conditions.
     *
     * @param value the value to validate
     * @return true if the value satisfies the directive, false otherwise
     */
    boolean validate(Object value);
}
