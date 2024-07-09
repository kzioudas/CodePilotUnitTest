package com.example.codepilotunittest.interfaces;

import com.example.testgenerator.core.TestCases;
import java.util.List;

public interface TestCaseGenerator {
    TestCases generateTestCases(Object input, List<Directive> directives);
}
