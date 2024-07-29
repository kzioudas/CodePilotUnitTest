package codepilotunittest.strategies;

import codepilotunittest.core.Directive;
import codepilotunittest.core.TestCases;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestCaseGenerator;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.TestStrategy;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTestingStrategy implements TestStrategy {

    private int Directive;

    public TestCases generateExceptionTests(Object input) {
        // Obtain the appropriate TestCaseGenerator for the input
        TestCaseGenerator generator = TestFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator with exception-triggering directives
        return generator.generateTestCases(input, createExceptionTriggeringDirectives());
    }

    private List<codepilotunittest.interfaces.Directive> createExceptionTriggeringDirectives() {
        List<Directive> directives = new ArrayList<>();
        // Directive to trigger NullPointerException
//        directives.add(testCase -> testCase.setInputParameters(null));
//
//        // Directive to trigger IndexOutOfBoundsException
//        directives.add(testCase -> testCase.setInputParameters("-1"));
//
//        // Directive to trigger IllegalArgumentException
//        directives.add(testCase -> testCase.setInputParameters("invalid input"));
//
//        // Directive to trigger IOException (simulated in environments where I/O is expected)
//        directives.add(testCase -> testCase.setInputParameters("invalid file path"));
//
//        // Directive to trigger ArithmeticException
//        directives.add(testCase -> testCase.setInputParameters("0")); // For division operations
//
//        // Additional directives can be added here for more specific exceptions
        return new ArrayList<>(Directive);
    }

    @Override
    public void generateTestCases(SrcElement srcElement) {

    }
}
