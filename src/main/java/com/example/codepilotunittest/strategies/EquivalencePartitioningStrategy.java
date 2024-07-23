package com.example.codepilotunittest.strategies;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import com.example.codepilotunittest.factories.TestFactory;
import com.example.codepilotunittest.interfaces.TestStrategy;

public class EquivalencePartitioningStrategy implements TestStrategy {

    public TestCases generateEquivalencePartitioningTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Equivalence conditions are assumed to be handled by the generator
    }
}
