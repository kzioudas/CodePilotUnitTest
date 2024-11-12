package codepilotunittest.directives;

import codepilotunittest.directives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DirectiveParser class.
 */
public class DirectiveParserTest {

    /**
     * Tests the parsing of a valid directive string.
     */
    @Test
    public void testParseValidDirectives() {
        // Arrange
        DirectiveParser directiveParser = new DirectiveParser();
        String directiveString = "param1: not null; param2: range(1-10); param3: notInRange(20-30); param4: null";

        // Act
        List<Directive> directives = directiveParser.parse(directiveString);

        // Assert
        assertEquals(4, directives.size());
        assertTrue(directives.get(0) instanceof NotNullDirective);
        assertEquals("param1", directives.get(0).getParameterName());

        assertTrue(directives.get(1) instanceof RangeDirective);
        assertEquals("param2", directives.get(1).getParameterName());
        assertEquals(1, ((RangeDirective) directives.get(1)).getMin());
        assertEquals(10, ((RangeDirective) directives.get(1)).getMax());

        assertTrue(directives.get(2) instanceof NotInRangeDirective);
        assertEquals("param3", directives.get(2).getParameterName());;
        assertEquals(20, ((NotInRangeDirective) directives.get(2)).getMin());
        assertEquals(30, ((NotInRangeDirective) directives.get(2)).getMax());
    }
    //ToDo
    /**
     * Tests the parsing of an invalid directive string.
     */
//    @Test
//    public void testParseInvalidDirectives() {
//        // Arrange
//        DirectiveParser directiveParser = new DirectiveParser();
//        String directiveString = "{[param1 invalidType], [param2 range(1, 10)]}";
//
//        // Act & Assert
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            directiveParser.parse(directiveString);
//        });
//
//        assertEquals("Invalid directive format: param1 invalidType", exception.getMessage());
//    }
}
