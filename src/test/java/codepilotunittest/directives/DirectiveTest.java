package codepilotunittest.directives;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Directive implementations.
 * This class tests the functionality of the different types of directives,
 * ensuring that they behave as expected with various inputs.
 */
public class DirectiveTest {

    /**
     * Tests the behavior of the NullDirective.
     */
    @Test
    public void testNullDirective() {
        Directive nullDirective = new NullDirective(Map.of("testParam", "null"), "null", "True", Map.of());

        assertEquals("null", nullDirective.getDirectiveType());
        assertFalse(nullDirective.validate("someValue"), "Expected 'someValue' to validate as false.");
    }


    /**
     * Tests the behavior of the RangeDirective.
     */
    @Test
    public void testRangeDirective() {
        Directive rangeDirective = new RangeDirective(Map.of("testParam", "5"), 1, 5,"true", "true", Map.of());

        assertEquals("range", rangeDirective.getDirectiveType());
        assertTrue(rangeDirective.validate(5));
        assertFalse(rangeDirective.validate(11));
        assertFalse(rangeDirective.validate(-1));
        assertFalse(rangeDirective.validate("notAnInteger"), "Expected non-integer to validate as false.");
    }



    /**
     * Tests the behavior of the SimpleValueDirective.
     */
    @Test
    public void testSimpleValueDirective() {
        Directive simpleValueDirective = new SimpleValueDirective(Map.of("testParam", "expectedValue"), "expectedValue", "True", Map.of());

        assertEquals("value", simpleValueDirective.getDirectiveType());
        assertTrue(simpleValueDirective.validate("expectedValue"), "Expected 'expectedValue' to validate as true.");
        assertFalse(simpleValueDirective.validate("unexpectedValue"), "Expected 'unexpectedValue' to validate as false.");
    }

    /**
     * Tests the behavior of the ThrowsExceptionDirective.
     */
    @Test
    public void testThrowsExceptionDirective() {
        Directive throwsExceptionDirective = new ThrowsExceptionDirective(Map.of("param1", "10", "param2", "0"), "ArithmeticException","ArithmeticException", Map.of());

        assertEquals("throwsException", throwsExceptionDirective.getDirectiveType());
        assertEquals("ArithmeticException", throwsExceptionDirective.getExpectedBehavior());
        assertEquals("ArithmeticException", throwsExceptionDirective.getExpectedResult());
    }
}
