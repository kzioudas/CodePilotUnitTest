package codepilotunittest.directives;

import java.util.Map;

/**
 * Interface representing a directive in a test case.
 * A directive defines specific conditions, behaviors, and validations
 * associated with a test case and provides methods to retrieve parameters
 * and generate test-related assertions.
 */
public interface Directive {

    /**
     * Retrieves all parameters associated with the directive.
     *
     * @return a map containing parameter names and their corresponding values
     */
    Map<String, String> getParameters();

    /**
     * Retrieves the parameter name by its index in the parameter list.
     *
     * @param index the index of the parameter
     * @return the parameter name at the specified index
     */
    String getParameterName(int index);

    /**
     * Retrieves the type of the directive.
     * This defines the category or purpose of the directive.
     *
     * @return a string representing the directive type
     */
    String getDirectiveType();

    /**
     * Retrieves the expected result of the directive.
     * This is typically used to validate test outcomes.
     *
     * @return the expected result as a string
     */
    String getExpectedResult();

    /**
     * Retrieves the expected behavior of the directive.
     * The behavior can specify actions such as "constructor" or other
     * test-related operations.
     *
     * @return the expected behavior as a string
     */
    String getExpectedBehavior();

    /**
     * Generates the assertion code for validating the directive.
     * The generated assertion can be included in test methods to validate
     * expected behaviors or outcomes.
     *
     * @return the assertion code as a string
     */
    String generateAssertion();

    /**
     * Retrieves the value of a specific parameter based on its key.
     *
     * @param key the name of the parameter
     * @return the value of the parameter as a string, or null if the key does not exist
     */
    String getParameterValue(String key);

    /**
     * Validates a given value against the directive's conditions.
     * This ensures that the value meets the constraints or expectations
     * defined by the directive.
     *
     * @param value the value to validate
     * @return true if the value satisfies the directive's conditions, false otherwise
     */
    boolean validate(Object value);

    /**
     * Checks if the directive is related to constructor initialization.
     * This is determined by comparing the directive's expected behavior
     * to "constructor".
     *
     * @return true if the directive applies to constructor initialization, false otherwise
     */
    default boolean isForConstructorInitialization() {
        return "constructor".equalsIgnoreCase(getExpectedBehavior());
    }

    /**
     * @return a map containing parameter names and values for constructor initialization
     */
    Map<String, String> getConstructorParameters();
}
