package codepilotunittest.testcases;

/**
 * Custom exception thrown when a test case is improperly formatted.
 */
public class TestCaseFormatException extends RuntimeException {

    /**
     * Constructs a new TestCaseFormatException with the specified detail message.
     *
     * @param message the detail message, which will be saved for later retrieval by the getMessage() method.
     */
    public TestCaseFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new TestCaseFormatException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception, which will be saved for later retrieval by the getCause() method.
     */
    public TestCaseFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
