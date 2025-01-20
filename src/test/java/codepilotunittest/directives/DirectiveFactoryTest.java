package codepilotunittest.directives;

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
        Directive directive = DirectiveFactory.createDirective(parameters,"null" ,"True");

        // Assert
        assertTrue(directive instanceof NullDirective);
        assertEquals("param1", directive.getParameterName(0));
    }


//    @Test
//    public void testCreateRangeDirective() {
//        // Arrange
//        String paramName = "param3";
//        String directiveString = "range(1-10)";
//
//        // Act
//        Directive directive = DirectiveFactory.createDirective(paramName,directiveString,"7","True" );
//
//        // Assert
//        assertTrue(directive instanceof RangeDirective);
//        RangeDirective rangeDirective = (RangeDirective) directive;
//        assertEquals("param3", rangeDirective.getParameterName());
//        assertEquals(1, rangeDirective.getMin());
//        assertEquals(10, rangeDirective.getMax());
//    }

//    @Test
//    public void testCreateNotInRangeDirective() {
//        // Arrange
//        String paramName = "param4";
//        String directiveString = "range(5-20)";
//
//        // Act
//        Directive directive = DirectiveFactory.createDirective(paramName,directiveString,"8","OutOfBoundsException");
//
//        // Assert
//        assertTrue(directive instanceof RangeDirective);
//        RangeDirective rangeDirective = (RangeDirective) directive;
//        assertEquals("param4", rangeDirective.getParameterName());
//        assertEquals(5, rangeDirective.getMin());
//        assertEquals(20, rangeDirective.getMax());
//    }

    @Test
    public void testCreateSimpleValueDirective() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param5","5");

        // Act
        Directive directive = DirectiveFactory.createDirective(parameters,"3","True");

        // Assert
        assertTrue(directive instanceof SimpleValueDirective);
        SimpleValueDirective simpleValueDirective = (SimpleValueDirective) directive;
        assertEquals("param5", simpleValueDirective.getParameterName(0));
        assertEquals("5", simpleValueDirective.getParameterValue("param5"));
    }
}
