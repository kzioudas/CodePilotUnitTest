package codepilotunittest.directives;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DirectiveFactoryTest {

    @Test
    public void testCreateNullDirective() {
        // Arrange

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1","null");

        // Act
        Directive directive = DirectiveFactory.createDirective(parameters,"null" ,"True",Map.of());

        // Assert
        assertTrue(directive instanceof NullDirective);
        assertEquals("param1", directive.getParameterName(0));
    }


    @Test
    public void testCreateRangeDirective() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param3", "range(1-10)");

        // Act
        Directive directive = DirectiveFactory.createDirective(parameters, "range(1-10)", "True",Map.of());

        // Assert
        assertTrue(directive instanceof RangeDirective, "Should create a RangeDirective");
        RangeDirective rangeDirective = (RangeDirective) directive;
        assertEquals("param3", rangeDirective.getParameterName(0), "Parameter name should be 'param3'");
        assertEquals(1, rangeDirective.getMin(), "Range min should be 1");
        assertEquals(10, rangeDirective.getMax(), "Range max should be 10");
    }

    @Test
    @DisplayName("Create ThrowsExceptionDirective")
    public void testCreateThrowsExceptionDirective() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param4", "throw");

        // Act
        Directive directive = DirectiveFactory.createDirective(parameters, "throw", "IllegalArgumentException",Map.of());

        // Assert
        assertTrue(directive instanceof ThrowsExceptionDirective, "Should create a ThrowsExceptionDirective");
        ThrowsExceptionDirective throwsExceptionDirective = (ThrowsExceptionDirective) directive;
        assertEquals("param4", throwsExceptionDirective.getParameterName(0), "Parameter name should be 'param4'");
        assertEquals("IllegalArgumentException", throwsExceptionDirective.getExpectedBehavior(), "Expected behavior should be 'IllegalArgumentException'");
    }

    @Test
    public void testCreateSimpleValueDirective() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param5","5");

        // Act
        Directive directive = DirectiveFactory.createDirective(parameters,"3","True",Map.of());

        // Assert
        assertTrue(directive instanceof SimpleValueDirective);
        SimpleValueDirective simpleValueDirective = (SimpleValueDirective) directive;
        assertEquals("param5", simpleValueDirective.getParameterName(0));
        assertEquals("5", simpleValueDirective.getParameterValue("param5"));
    }
}
