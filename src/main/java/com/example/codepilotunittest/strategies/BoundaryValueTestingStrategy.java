package com.example.codepilotunittest.strategies;


import com.example.codepilotunittest.core.TestCases;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BoundaryValueTestingStrategy {

    public TestCases generateBoundaryValueTests(java.lang.Class<?> clazz) {
        // Prepare specific directives for boundary value testing
        List<Directive> directives = new ArrayList<>();
        // Example directive: ensure boundary conditions are tested for each numerical field
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(int.class) || field.getType().equals(double.class)) {
                // Add directive to test boundary values
                directives.add(new BoundaryDirective(field));
            }
        }

        TestCaseGenerator generator = TestCaseGeneratorFactory.getTestCaseGenerator(input);

        // Generate test cases using the determined generator
        return generator.generateTestCases(input, directives);
    }
}
