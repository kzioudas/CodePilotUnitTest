package codepilotunittest.directives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectiveFactoryTest {

    @Test
    public void testCreateNullDirective() {
        // Arrange
        String paramName = "param1";
        String directiveString = "null";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName,directiveString,"null" ,"True");

        // Assert
        assertTrue(directive instanceof NullDirective);
        assertEquals("param1", directive.getParameterName());
    }

    @Test
    public void testCreateNotNullDirective() {
        // Arrange
        String paramName = "param2";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName,"null","null" ,"False");

        // Assert
        assertTrue(directive instanceof NullDirective);
        assertEquals("param2", directive.getParameterName());
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
        String paramName = "param5";
        String directiveString = "5";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName,directiveString,"3","True");

        // Assert
        assertTrue(directive instanceof SimpleValueDirective);
        SimpleValueDirective simpleValueDirective = (SimpleValueDirective) directive;
        assertEquals("param5", simpleValueDirective.getParameterName());
        assertEquals("5", simpleValueDirective.getParameterValue());
    }
}
