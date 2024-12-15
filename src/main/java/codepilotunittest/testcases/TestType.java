package codepilotunittest.testcases;

/**
 * Enum representing different types of test cases.
 * This makes the test types more type-safe and easier to maintain.
 */
public enum TestType {
    HAPPY_PATH,  // Represents the positive flow
    RAINY_DAY;   // Represents edge cases or error scenarios

    /**
     * Parses a string to match the corresponding TestType.
     *
     * @param type the string representation of the test type
     * @return the corresponding TestType
     * @throws IllegalArgumentException if no matching TestType is found
     */
    public static TestType fromString(String type) {
        switch (type.toUpperCase()) {
            case "HAPPY_PATH":
                return HAPPY_PATH;
            case "RAINY_DAY":
                return RAINY_DAY;
            default:
                throw new IllegalArgumentException("Unknown test type: " + type);
        }
    }
}
