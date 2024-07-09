package com.example.codepilotunittest.interfaces;

import com.example.codepilotunittest.annotations.TestConfig;
import com.example.codepilotunittest.core.TestCase;
import com.example.codepilotunittest.core.TestCases;
import com.example.codepilotunittest.directives.Directive;
import java.lang.reflect.Method;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JUnitTestGenerator {

    public static void generateJUnitTests(Class<?> clazz, TestCases testCases, List<Directive> directives) throws Exception {
        StringBuilder testClassContent = new StringBuilder();
        String className = clazz.getSimpleName() + "Tests";

        testClassContent.append("import static org.junit.Assert.*;\n");
        testClassContent.append("import org.junit.Test;\n\n");
        testClassContent.append("public class " + className + " {\n");

        for (TestCase testCase : testCases.getTestCaseList()) {
            testClassContent.append(generateJUnitTestForTestCase(clazz, testCase, directives));
        }

        testClassContent.append("}\n");
        writeToFile(className + ".java", testClassContent.toString());
    }

    private static String generateJUnitTestForTestCase(Class<?> clazz, TestCase testCase, List<Directive> directives) {
        StringBuilder methodContent = new StringBuilder();
        String methodName = "test" + testCase.getMethodName().substring(0, 1).toUpperCase() + testCase.getMethodName().substring(1);

        methodContent.append("    @Test\n");
        if (testCase.getExpectedException() != null) {
            methodContent.append("    @Test(expected = " + testCase.getExpectedException().getSimpleName() + ".class)\n");
        }
        methodContent.append("    public void " + methodName + "() throws Exception {\n");
        methodContent.append("        " + clazz.getSimpleName() + " instance = new " + clazz.getSimpleName() + "();\n");

        methodContent.append("        assertEquals(");
        methodContent.append(testCase.getExpectedResult());
        methodContent.append(", instance." + testCase.getMethodName() + "(");
        methodContent.append(generateParameterValues(testCase.getParameters(), directives));
        methodContent.append("));\n");

        methodContent.append("    }\n\n");
        return methodContent.toString();
    }

    private static String generateParameterValues(List<Object> parameters, List<Directive> directives) {
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) params.append(", ");
            // Here, you could enhance this to check for specific directives related to parameters
            params.append(parameters.get(i).toString());  // Assuming toString gives a valid representation
        }
        return params.toString();
    }

    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
