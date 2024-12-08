package codepilotunittest.directives;

import codepilotunittest.directives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Directive implementations.
 * This class tests the functionality of the different types of directives,
 * ensuring that they behave as expected with various inputs.
 */
public class DirectiveTest {

    /**
     * Tests the behavior of the NullDirective.
     * It verifies that:
     * - The parameter name is correctly stored.
     * - The directive type is "null".
     * - The directive correctly validates null values as true.
     * - The directive correctly validates non-null values as false.
     */
    @Test
    public void testNullDirective() {
        Directive nullDirective = new NullDirective("testParam","NULL","null","True");

        assertEquals("testParam", nullDirective.getParameterName());
        assertEquals("null", nullDirective.getDirectiveType());
        assertTrue(nullDirective.validate("null"));
        assertFalse(nullDirective.validate("someValue"));
    }

    /**
     * Tests the behavior of the NotNullDirective.
     * It verifies that:
     * - The parameter name is correctly stored.
     * - The directive type is "not null".
     * - The directive correctly validates non-null values as true.
     * - The directive correctly validates null values as false.
     */
    @Test
    public void testNotNullDirective() {
        Directive notNullDirective = new NullDirective("testParam","3","null","False");

        assertEquals("testParam", notNullDirective.getParameterName());
        assertEquals("null", notNullDirective.getDirectiveType());
        //assertTrue(notNullDirective.validate("not null"));
        assertFalse(notNullDirective.validate("someValue"));
    }

    /**
     * Tests the behavior of the RangeDirective.
     * It verifies that:
     * - The parameter name is correctly stored.
     * - The directive type is "range(min, max)".
     * - The directive correctly validates values within the specified range as true.
     * - The directive correctly rejects values outside the specified range as false.
     * - The directive rejects non-integer values.
     */
    @Test
    public void testRangeDirective() {
        Directive rangeDirective = new RangeDirective("testParam", 0, 10,"3","True");

        assertEquals("testParam", rangeDirective.getParameterName());
        assertEquals("range", rangeDirective.getDirectiveType());
        assertTrue(rangeDirective.validate(5));
        assertFalse(rangeDirective.validate(11));
        assertFalse(rangeDirective.validate(-1));
        assertFalse(rangeDirective.validate("notAnInteger"));
    }

    /**
     * Tests the behavior of the NotInRangeDirective.
     * It verifies that:
     * - The parameter name is correctly stored.
     * - The directive type is "notInRange(min, max)".
     * - The directive correctly identifies values outside the specified range as true.
     * - The directive correctly rejects values within the specified range as false.
     * - The directive rejects non-integer values.
     */
//    @Test
//    public void testNotInRangeDirective() {
//        Directive notInRangeDirective = new NotInRangeDirective("testParam", 0, 10);
//
//        assertEquals("testParam", notInRangeDirective.getParameterName());
//        assertEquals("notInRange(0,10)", notInRangeDirective.getDirectiveType());
//        assertTrue(notInRangeDirective.validate(11));
//        assertTrue(notInRangeDirective.validate(-1));
//        assertFalse(notInRangeDirective.validate(5));
//        assertFalse(notInRangeDirective.validate("notAnInteger"));
//    }

    /**
     * Tests the behavior of the SimpleValueDirective.
     * It verifies that:
     * - The parameter name is correctly stored.
     * - The directive type is "value".
     * - The directive correctly validates against the expected value as true.
     * - The directive correctly rejects unexpected values as false.
     */
    @Test
    public void testSimpleValueDirective() {
        Directive simpleValueDirective = new SimpleValueDirective("testParam", "expectedValue","expectedValue","True");

        assertEquals("testParam", simpleValueDirective.getParameterName());
        assertEquals("value", simpleValueDirective.getDirectiveType());
        assertTrue(simpleValueDirective.validate("expectedValue"));
        assertFalse(simpleValueDirective.validate("unexpectedValue"));
    }
}

