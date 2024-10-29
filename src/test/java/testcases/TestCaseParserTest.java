package testcases;

import codepilotunittest.core.MainEngine;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodNotFoundException;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;
import codepilotunittest.testcases.*;
import org.junit.jupiter.api.Test;
import utils.PathTemplate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCaseParserTest {
    private MainEngine mainEngine;
    private ProjectRepresentation projectRepresentation;
    private Path sourcePackagePath;
    private ClassRepresentation classRepresentation;
    private MethodRepresentation methodRepresentation;
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
        List<TestCase> testCases = testCaseParser.parseTestCases(Path.of("src/test/resources/LatexEditor/testcases.csv"));

        // Assert: Check that we have two test cases
        assertEquals(2, testCases.size());

        // First TestCase (RainyDay)
        TestCase testCase1 = testCases.get(0);
        assertTrue(testCase1 instanceof RainyDayTestCase);
        assertEquals("testInitializeEditor", testCase1.getTestName());
        assertEquals(TestType.RAINY_DAY, testCase1.getTestType());
        assertEquals(projectRepresentation.findClass("LatexEditorController"), testCase1.getClassToTest());
        assertEquals(projectRepresentation.findClass("LatexEditorController").findMethod("LatexEditorController"), testCase1.getMethodToTest());

        //assertTrue(testCase1.getDirectives().get(0) instanceof RainyDayTestCase);

        // Second TestCase (HappyPath)
        TestCase testCase2 = testCases.get(1);
        assertTrue(testCase2 instanceof HappyPathTestCase);
        assertEquals("VersionsManager", testCase2.getTestName());
        assertEquals(TestType.HAPPY_PATH, testCase2.getTestType());
        assertEquals(projectRepresentation.findClass("VersionsManager"), testCase2.getClassToTest());
        assertEquals(projectRepresentation.findClass("VersionsManager").findMethod("setStrategy"), testCase2.getMethodToTest());
        //assertEquals(List.of("dirA", "dirB"), testCase2.getDirectives());
    }
}
