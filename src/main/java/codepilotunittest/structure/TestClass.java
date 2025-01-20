package codepilotunittest.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JUnit test class.
 */
public class TestClass {
    private final String className;
    private final List<TestMethod> methods;

    /**
     * Constructor to initialize the test class.
     *
     * @param className The name of the class being tested.
     */
    public TestClass(String className) {
        this.className = className;
        this.methods = new ArrayList<>();
    }

    /**
     * Adds a test method to the class.
     *
     * @param method The TestMethod object to add.
     */
    public void addMethod(TestMethod method) {
        methods.add(method);
    }

    /**
     * Renders the test class as a string.
     *
     * @return The rendered test class code.
     */
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("import org.junit.jupiter.api.Test;\n")
                .append("import static org.junit.jupiter.api.Assertions.*;\n\n")
                .append("public class ").append(className).append("Test {\n\n");
        for (TestMethod method : methods) {
            sb.append(method.render()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
