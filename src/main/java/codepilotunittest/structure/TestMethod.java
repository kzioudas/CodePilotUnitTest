package codepilotunittest.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JUnit test method.
 */
public class TestMethod {
    private final String name;
    private final List<String> initializationBlocks;
    private final List<String> parameterPreparations;
    private final String methodCall;
    private final List<String> assertions;

    /**
     * Constructor to initialize the test method.
     *
     * @param name       The name of the test method.
     * @param methodCall The method call being tested.
     */
    public TestMethod(String name, String methodCall) {
        this.name = name;
        this.methodCall = methodCall;
        this.initializationBlocks = new ArrayList<>();
        this.parameterPreparations = new ArrayList<>();
        this.assertions = new ArrayList<>();
    }

    /**
     * Adds an initialization block to the test method.
     *
     * @param initialization The initialization block string.
     */
    public void addInitialization(String initialization) {
        initializationBlocks.add(initialization);
    }

    /**
     * Adds a parameter preparation statement to the test method.
     *
     * @param preparation The parameter preparation string.
     */
    public void addParameterPreparation(String preparation) {
        parameterPreparations.add(preparation);
    }

    /**
     * Adds an assertion to the test method.
     *
     * @param assertion The assertion string.
     */
    public void addAssertion(String assertion) {
        assertions.add(assertion);
    }

    /**
     * Renders the test method as a string.
     *
     * @return The rendered test method code.
     */
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Test\n")
                .append("    void ").append(name).append("() {\n");

        for (String block : initializationBlocks) {
            sb.append("        ").append(block).append("\n");
        }

        if (!parameterPreparations.isEmpty()) {
            sb.append("\n        // Prepare method parameters\n");
            for (String preparation : parameterPreparations) {
                sb.append("        ").append(preparation).append("\n");
            }
        }

        sb.append("\n        // Call the method\n")
                .append("        ").append(methodCall).append("\n");

        if (!assertions.isEmpty()) {
            sb.append("\n        // Assertions\n");
            for (String assertion : assertions) {
                sb.append("        ").append(assertion).append("\n");
            }
        }

        sb.append("    }\n");
        return sb.toString();
    }
}
