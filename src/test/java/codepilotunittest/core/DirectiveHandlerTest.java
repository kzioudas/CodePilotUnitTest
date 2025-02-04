package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.directives.SimpleValueDirective;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectiveHandlerTest {
    @Test
    @DisplayName("Validate Directive Value Parsing")
    public void testDirectiveValueParsing() {
        // Arrange: Create parameters for the directive
        Map<String, String> parameters = Map.of(
                "param1", "5",
                "param2", "10"
        );

        // Create a directive instance
        Directive directive = new SimpleValueDirective(parameters, "ExpectedResult", "ExpectedBehavior", Map.of());

        // Act: Retrieve parameter values
        String param1Value = directive.getParameterValue("param1");
        String param2Value = directive.getParameterValue("param2");
        String expectedResultValue = directive.getExpectedResult();
        String expectedBehavior = directive.getExpectedBehavior();

        // Assert: Verify values
        assertEquals("ExpectedResult", expectedResultValue);
        assertEquals("ExpectedBehavior", expectedBehavior);
        assertEquals("5", param1Value, "param1 value should be 5");
        assertEquals("10", param2Value, "param2 value should be 10");
    }
}
