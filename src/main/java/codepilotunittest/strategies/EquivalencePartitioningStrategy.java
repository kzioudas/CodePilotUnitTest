package codepilotunittest.strategies;

import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.TestStrategy;

public class EquivalencePartitioningStrategy implements TestStrategy {

    public TestCases generateEquivalencePartitioningTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Equivalence conditions are assumed to be handled by the generator
    }

    /**
     * @param srcElement
     */
    @Override
    public void generateTestCases(SrcElement srcElement) {

    }
}
