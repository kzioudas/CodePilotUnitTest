package codepilotunittest.directives;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DirectiveParser class.
 */
public class DirectiveParserTest {

    /**
     * Tests parsing of a valid directive string into a SimpleValueDirective.
     */
    @Test
    public void testParseSimpleValueDirective() {
        // Arrange
        String parametersArray = "param1:5;param2:10";
        String expectedResult = "15";
        String expectedBehavior = "true";
        String constructorParameters = "param1:5;param2:10";
        // Act
        Directive directive = DirectiveParser.parseDirective(parametersArray, expectedResult, expectedBehavior,constructorParameters);

        // Assert
        assertTrue(directive instanceof SimpleValueDirective, "Expected a SimpleValueDirective");
        SimpleValueDirective simpleDirective = (SimpleValueDirective) directive;
        Map<String, String> parameters = simpleDirective.getParameters();

        assertEquals(2, parameters.size());
        assertEquals("5", parameters.get("param1"));
        assertEquals("10", parameters.get("param2"));
        assertEquals(expectedResult, simpleDirective.getExpectedResult());
        assertEquals(expectedBehavior, simpleDirective.getExpectedBehavior());
    }

    /**
     * Tests parsing of a directive with a range parameter.
     */
    @Test
    public void testParseRangeDirective() {
        // Arrange
        String parametersArray = "param1:5;param2:10";
        String expectedResult = "range(1-10)";
        String expectedBehavior = "true";
        String constructorParameters = "param1:5;param2:10";

        // Act
        Directive directive = DirectiveParser.parseDirective(parametersArray, expectedResult, expectedBehavior, constructorParameters);

        // Assert
        assertTrue(directive instanceof RangeDirective, "Expected a RangeDirective");
        RangeDirective rangeDirective = (RangeDirective) directive;

        Map<String, String> parameters = rangeDirective.getParameters();
        Map<String, String> cParameters = rangeDirective.getConstructorParameters();
        assertEquals(2, parameters.size());
        assertEquals("5", cParameters.get("param1"));
        assertEquals("10", cParameters.get("param2"));
        assertTrue(parameters.containsKey("param1"));
        assertEquals(expectedResult, rangeDirective.getExpectedResult());
        assertEquals(expectedBehavior, rangeDirective.getExpectedBehavior());
    }

    /**
     * Tests parsing of a directive with an exception behavior.
     */
    @Test
    public void testParseThrowsExceptionDirective() {
        // Arrange
        String parametersArray = "param1:10;param2:0";
        String expectedResult = null;
        String expectedBehavior = "ArithmeticException";
        String constructorParameters = "param1:10;param2:0";
        // Act
        Directive directive = DirectiveParser.parseDirective(parametersArray, expectedResult, expectedBehavior, constructorParameters);

        // Assert
        assertTrue(directive instanceof ThrowsExceptionDirective, "Expected a ThrowsExceptionDirective");
        ThrowsExceptionDirective exceptionDirective = (ThrowsExceptionDirective) directive;

        Map<String, String> parameters = exceptionDirective.getParameters();
        assertEquals(2, parameters.size());
        assertEquals("10", parameters.get("param1"));
        assertEquals("0", parameters.get("param2"));
        assertEquals(expectedResult, exceptionDirective.getExpectedResult());
        assertEquals(expectedBehavior, exceptionDirective.getExpectedBehavior());
    }

    /**
     * Tests parsing of an invalid parameter format.
     */
    @Test
    public void testParseInvalidParameterFormat() {
        // Arrange
        String parametersArray = "param1:5;invalidFormat";
        String expectedResult = "15";
        String expectedBehavior = "true";
        String constructorParameters =  "param1:5;invalidFormat";
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                DirectiveParser.parseDirective(parametersArray, expectedResult, expectedBehavior, constructorParameters)
        );

        assertTrue(exception.getMessage().contains("Invalid parameter format"), "Expected invalid format exception");
    }

}
