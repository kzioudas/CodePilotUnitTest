package com.example.codepilotunittest.testcasegeneration;

import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.interfaces.TestCaseGenerator;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Class implements TestCaseGenerator {
    @Override
    public TestCases generateTestCases(Object input, List<Directive> directives) {
        TestCases testCases = new TestCases();
        if (input instanceof java.lang.Class<?>) {
            java.lang.Class<?> clazz = (java.lang.Class<?>) input;
            Method testCaseGenerator = new Method();
            Arrays.stream(clazz.getDeclaredMethods())
                    .forEach(method -> testCases.merge(testCaseGenerator.generateTestCases(method, directives)));
        }
        return testCases;
    }
}
