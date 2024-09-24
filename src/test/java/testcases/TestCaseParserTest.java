package testcases;

import codepilotunittest.testcases.HappyPathTestCase;
import codepilotunittest.testcases.RainyDayTestCase;
import codepilotunittest.testcases.TestCaseParser;
import codepilotunittest.testcases.TestCase;
import org.junit.jupiter.api.Test;
import utils.PathTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCaseParserTest {

    @Test
    public void testParseTestCasesFromFile() throws Exception {


        // Use TestCaseParser to parse the file
        TestCaseParser testCaseParser = new TestCaseParser() ;

        // Act: Parse the test cases
        List<TestCase> testCases = testCaseParser.parseTestCases(PathTemplate.TestCases.FILE.path);

        // Assert: Check that we have two test cases
        assertEquals(2, testCases.size());

        // First TestCase (RainyDay)
        TestCase testCase1 = testCases.get(0);
        assertTrue(testCase1 instanceof RainyDayTestCase);
        assertEquals("test1", testCase1.getTestName());
        assertEquals("rainyday", testCase1.getTestType());
        assertEquals("ClassToTest", testCase1.getClassToTest());
        assertEquals("testMethod", testCase1.getMethodToTest());
        assertEquals(List.of("dir1", "dir2", "dir3"), testCase1.getDirectives());

        // Second TestCase (HappyPath)
        TestCase testCase2 = testCases.get(1);
        assertTrue(testCase2 instanceof HappyPathTestCase);
        assertEquals("test2", testCase2.getTestName());
        assertEquals("happypath", testCase2.getTestType());
        assertEquals("ClassToTest2", testCase2.getClassToTest());
        assertEquals("anotherTestMethod", testCase2.getMethodToTest());
        assertEquals(List.of("dirA", "dirB"), testCase2.getDirectives());
    }
}
