package codepilotunittest.strategies;


import codepilotunittest.core.Directive;
import codepilotunittest.core.TestCase;
import codepilotunittest.factories.TestFactory;
import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.interfaces.TestStrategy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BoundaryValueTestingStrategy implements TestStrategy{

    private TestFactory testFactory = new TestFactory();

    @Override
    public void generateTestCases(SrcElement srcElement) {
        TestCase testCase = testFactory.createTestCase();

        List<Directive> directives = new ArrayList<>();
        // Example directive: ensure boundary conditions are tested for each numerical field
//        for (Field field : clazz.getDeclaredFields()) {
//            if (field.getType().equals(int.class) || field.getType().equals(double.class)) {
//                // Add directive to test boundary values
//                directives.add(new Directive());
//            }
//        }




    }

}

