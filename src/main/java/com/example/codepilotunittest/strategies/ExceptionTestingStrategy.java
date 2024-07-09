package com.example.codepilotunittest.strategies;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import com.example.codepilotunittest.testcasegeneration.TestCaseGeneratorFactory;
import java.util.ArrayList;
import java.util.List;

public class ExceptionTestingStrategy {

    public TestCases generateExceptionTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestCaseGeneratorFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator with exception-triggering directives
        return generator.generateTestCases(input, createExceptionTriggeringDirectives());
    }

    private List<Directive> createExceptionTriggeringDirectives() {
        List<Directive> directives = new ArrayList<>();
        // Directive to trigger NullPointerException
        directives.add(testCase -> testCase.setInputParameters(null));

        // Directive to trigger IndexOutOfBoundsException
        directives.add(testCase -> testCase.setInputParameters("-1"));

        // Directive to trigger IllegalArgumentException
        directives.add(testCase -> testCase.setInputParameters("invalid input"));

        // Directive to trigger IOException (simulated in environments where I/O is expected)
        directives.add(testCase -> testCase.setInputParameters("invalid file path"));

        // Directive to trigger ArithmeticException
        directives.add(testCase -> testCase.setInputParameters("0")); // For division operations

        // Additional directives can be added here for more specific exceptions
        return directives;
    }
}
