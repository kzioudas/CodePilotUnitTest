package codepilotunittest.testcases;

import codepilotunittest.core.MainEngine;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.*;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCaseParserTest {
    private MainEngine mainEngine;
    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;
    private TestCaseParser testCaseParser;
    public void setUp()  {
        sourcePackagePath = Path.of("src/test/resources/LatexEditor");
        mainEngine = new MainEngine(sourcePackagePath,"LatexEditor");
        projectRepresentation = mainEngine.getProjectRepresentation();

    }
    @Test
    public void testParseTestCasesFromFile() throws Exception {
        setUp();
        // Use TestCaseParser to parse the file
        testCaseParser = new TestCaseParser(projectRepresentation);

        // Act: Parse the test cases
        Map<String, List<TestCase>> testCases = testCaseParser.parseTestCases(Path.of("src/test/resources/LatexEditor/testcases.csv"));

        // Assert: Check that we have two test cases
        assertEquals(3, testCases.size());

        // First TestCase (RainyDay)
        TestCase testCase1 = testCases.get("VersionsManager").get(0);
        assertTrue(testCase1 instanceof HappyPathTestCase);
        //assertEquals("setStrategy", testCase1.getTestName());
        assertEquals(TestType.HAPPY_PATH, testCase1.getTestType());
        assertEquals(projectRepresentation.findClass("VersionsManager"), testCase1.getClassToTest());
        try {
            assertEquals(projectRepresentation.findClass("VersionsManager").findMethod("setStrategy"), testCase1.getMethodToTest());
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        }

        //assertTrue(testCase1.getDirectives().get(0) instanceof RainyDayTestCase);

        // Second TestCase (HappyPath)
        TestCase testCase2 = testCases.get("VersionsManager").get(0);
        assertTrue(testCase2 instanceof HappyPathTestCase);
        //assertEquals("VersionsManager", testCase2.getTestName());
        assertEquals(TestType.HAPPY_PATH, testCase2.getTestType());
        assertEquals(projectRepresentation.findClass("VersionsManager"), testCase2.getClassToTest());
        try {
            assertEquals(projectRepresentation.findClass("VersionsManager").findMethod("setStrategy"), testCase2.getMethodToTest());
        } catch (MethodNotFoundException e) {
            throw new RuntimeException(e);
        }
        //assertEquals(List.of("dirA", "dirB"), testCase2.getDirectives());
    }
}
