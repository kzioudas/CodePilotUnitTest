package codepilotunittest.directives;

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
        String directiveString = "param1:null:null:True; param2:range(1-10):'3':True;";

        // Act
        List<Directive> directives = directiveParser.parse(directiveString);

        // Assert
        assertEquals(2, directives.size());
        assertTrue(directives.get(0) instanceof NullDirective);
        assertEquals("param1", directives.get(0).getParameterName());

        assertTrue(directives.get(1) instanceof SimpleValueDirective);
        assertEquals("param2", directives.get(1).getParameterName());
        //assertEquals(1, ((SimpleValueDirective) directives.get(1)).getMin());
        //assertEquals(10, ((SimpleValueDirective) directives.get(1)).getMax());
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
