package codepilotunittest.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public String getMethodName() {
        return "";
    }

    public Class<Object> getExpectedException() {
        return null;
    }

    public char[] getExpectedResult() {
        return new char[0];
    }

    public List<Object> getParameters() {
        return new ArrayList<>();
    }
}
