package codepilotunittest.strategies;

import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.TestStrategy;

public class DecisionBasedTestingStrategy implements TestStrategy {

    public TestCases generateDecisionBasedTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Decision paths should be part of the test case logic
    }

    /**
     * @param srcElement
     */
    @Override
    public void generateTestCases(SrcElement srcElement) {

    }
}
