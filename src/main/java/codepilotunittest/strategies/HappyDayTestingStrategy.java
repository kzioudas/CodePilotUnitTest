package codepilotunittest.strategies;

import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.TestStrategy;

public class HappyDayTestingStrategy implements TestStrategy {

    public TestCases generateHappyDayTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Assuming no directives are needed
    }

    @Override
    public void generateTestCases(SrcElement srcElement) {

    }
}
