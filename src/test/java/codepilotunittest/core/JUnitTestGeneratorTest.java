package codepilotunittest.core;

import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.TestCase;
import codepilotunittest.testcases.TestCaseParser;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitTestGeneratorTest {
    @Test
    @DisplayName("Generate Tests for Real Class")
    public void testGenerateTestsForRealClass() throws IOException {
        // Arrange: Setup directories and files
        Path sourceDir = Path.of("src/test/resources/example-project1/src/main/java/example");
        Path testDir = Path.of("src/test/resources/example-project1/src/test/java/example");
        Path testCaseDir = Path.of("src/test/resources/example-project1");

        // Ensure directories exist
        Files.createDirectories(sourceDir);
        Files.createDirectories(testDir);

        // Delete the file if it exists to avoid AccessDeniedException
        Path userTestCasePath = testCaseDir.resolve("testCasesJunit.csv");
        if (Files.exists(userTestCasePath)) {
            Files.delete(userTestCasePath);
        }

        // Create a dummy class file
        Path userClassPath = sourceDir.resolve("User.java");
        Files.writeString(userClassPath, "package example; public class User { public int add(int a, int b) { return a + b; } }");

        // Create a dummy testcases file
        Files.writeString(userTestCasePath, "test_type|class_name|method_name|directives|expected_result|expected_behavior\n" +
                "rainyday|User|add|a:10;b:1|11|True");

        ProjectRepresentation projectRepresentation = new MainEngine(sourceDir, "example-project").getProjectRepresentation();
        JUnitTestGenerator generator = new JUnitTestGenerator(projectRepresentation);

        generator.generateTests(new TestCaseParser(projectRepresentation).parseTestCases(userTestCasePath));

        // Assert: Check if the test file was created
        Path testFilePath = testDir.resolve("UserTest.java");
        assertTrue(Files.exists(testFilePath), "Test file should be created.");

        // Expected content (using traditional concatenation)
        String expectedContent = "import org.junit.jupiter.api.Test;\n"
                + "import static org.junit.jupiter.api.Assertions.*;\n\n"
                + "public class UserTest {\n\n"
                + "    @Test\n"
                + "    void testadd_rainy_day() {\n"
                + "        User instance = new User();\n\n"
                + "        // Prepare method parameters\n"
                + "        int a = 10;\n"
                + "        int b = 1;\n\n"
                + "        // Call the method\n"
                + "        int result = instance.add(10, 1);\n\n"
                + "        // Assertions\n"
                + "        assertEquals(result, 11, \"Expected result to equal 11\");\n\n"
                + "    }\n\n"
                + "}";

        // Read actual content
        String actualContent = Files.readString(testFilePath);

        String normalizedExpectedContent = expectedContent.strip().replace("\r\n", "\n");
        String normalizedActualContent = actualContent.strip().replace("\r\n", "\n");
        // Assert: Content matches
        assertEquals(normalizedExpectedContent, normalizedActualContent);
    }

}

