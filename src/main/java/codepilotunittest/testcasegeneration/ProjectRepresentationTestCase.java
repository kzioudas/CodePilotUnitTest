package codepilotunittest.testcasegeneration;

import codepilotunittest.interfaces.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.TestCaseGenerator;
import java.util.List;

public class ProjectRepresentationTestCase implements TestCaseGenerator {
    private List<java.lang.Class<?>> classes;  // Assuming a list of Class objects are part of the project

    public ProjectRepresentationTestCase(List<java.lang.Class<?>> classes) {
        this.classes = classes;
    }

    public ProjectRepresentationTestCase() {

    }

    @Override
    public TestCases generateTestCases(Object input, List<Directive> directives) {
        TestCases projectTestCases = new TestCases();

        return projectTestCases;
    }
}


