package codepilotunittest.strategies;

import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.TestStrategy;

public class RainyDayTestingStrategy implements TestStrategy {

    public TestCases generateRainyDayTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Assuming specific error-handling directives if needed
    }

    @Override
    public void generateTestCases(SrcElement srcElement) {

    }
}
