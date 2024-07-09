package com.example.codepilotunittest.strategies;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import com.example.codepilotunittest.testcasegeneration.TestCaseGeneratorFactory;

public class HappyDayTestingStrategy {

    public TestCases generateHappyDayTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestCaseGeneratorFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, null);  // Assuming no directives are needed
    }
}
