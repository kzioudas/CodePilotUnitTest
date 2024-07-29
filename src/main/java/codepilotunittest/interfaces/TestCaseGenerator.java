package codepilotunittest.interfaces;

import codepilotunittest.core.TestCases;
import java.util.List;

public interface TestCaseGenerator {
    TestCases generateTestCases(Object input, List<Directive> directives);
}
