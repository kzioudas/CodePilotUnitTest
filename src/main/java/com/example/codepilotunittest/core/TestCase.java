package com.example.codepilotunittest.core;

import java.util.HashMap;
import java.util.Map;

public class TestCase {
    private Map<String, Object> parameters = new HashMap<>();
    private Object expectedResult;

    public void setParameter(String name, Object value) {
        parameters.put(name, value);
    }

    public void setExpectedResult(Object result) {
        expectedResult = result;
    }
}
