package com.example.codepilotunittest.strategies;


import com.example.codepilotunittest.core.TestCases;
import java.lang.reflect.Field;
import java.util.ArrayList;
import codepilotunittest.interfaces.TestStrategy;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.core.TestCase;
import codepilotunittest.factories.TestFactory;

public class BoundaryValueTestingStrategy implements TestStrategy{

    private TestFactory testFactory = new TestFactory();

    @Override
    public void generateTestCases(SrcElement srcElement) {
        TestCase testCase = testFactory.createTestCase();

        List<Directive> directives = new ArrayList<>();
        // Example directive: ensure boundary conditions are tested for each numerical field
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(int.class) || field.getType().equals(double.class)) {
                // Add directive to test boundary values
                directives.add(new BoundaryDirective(field));
            }
        }



        // Generate test cases using the determined generator
        return generator.generateTestCases(input, directives);
    }

}

