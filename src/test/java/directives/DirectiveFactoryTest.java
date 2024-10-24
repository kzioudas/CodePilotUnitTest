package directives;

import codepilotunittest.directives.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectiveFactoryTest {

    @Test
    public void testCreateNullDirective() {
        // Arrange
        String paramName = "param1";
        String directiveString = "null";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName, directiveString);

        // Assert
        assertTrue(directive instanceof NullDirective);
        assertEquals("param1", directive.getParameterName());
    }

    @Test
    public void testCreateNotNullDirective() {
        // Arrange
        String paramName = "param2";
        String directiveString = "not null";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName, directiveString);

        // Assert
        assertTrue(directive instanceof NotNullDirective);
        assertEquals("param2", directive.getParameterName());
    }

    @Test
    public void testCreateRangeDirective() {
        // Arrange
        String paramName = "param3";
        String directiveString = "range(1-10)";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName, directiveString);

        // Assert
        assertTrue(directive instanceof RangeDirective);
        RangeDirective rangeDirective = (RangeDirective) directive;
        assertEquals("param3", rangeDirective.getParameterName());
        assertEquals(1, rangeDirective.getMin());
        assertEquals(10, rangeDirective.getMax());
    }

    @Test
    public void testCreateNotInRangeDirective() {
        // Arrange
        String paramName = "param4";
        String directiveString = "notInRange(5-20)";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName, directiveString);

        // Assert
        assertTrue(directive instanceof NotInRangeDirective);
        NotInRangeDirective notInRangeDirective = (NotInRangeDirective) directive;
        assertEquals("param4", notInRangeDirective.getParameterName());
        assertEquals(5, notInRangeDirective.getMin());
        assertEquals(20, notInRangeDirective.getMax());
    }

    @Test
    public void testCreateSimpleValueDirective() {
        // Arrange
        String paramName = "param5";
        String directiveString = "5";

        // Act
        Directive directive = DirectiveFactory.createDirective(paramName, directiveString);

        // Assert
        assertTrue(directive instanceof SimpleValueDirective);
        SimpleValueDirective simpleValueDirective = (SimpleValueDirective) directive;
        assertEquals("param5", simpleValueDirective.getParameterName());
        assertEquals("5", simpleValueDirective.getParameterValue());
    }
}
