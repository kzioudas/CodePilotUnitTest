package codepilotunittest.representations;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.ModifierType;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a method in a Java class, including its name, parameters, and modifiers.
 */
public class MethodRepresentation implements SrcElement {
    private final String methodName;
    private final String returnType;
    private final Map<String, String> parameters;
    private final List<ModifierType> modifiers;
    private final Set<Relationship<LeafNode>> relationships;

    /**
     * Constructor for MethodRepresentation.
     *
     * @param methodName The name of the method.
     * @param returnType The return type of the method.
     * @param parameters The map of parameter names to their types.
     * @param modifiers The list of modifiers (e.g., public, static) for the method.
     * @param relationships The relationships involving this method.
     */
    public MethodRepresentation(
            String methodName,
            String returnType,
            Map<String, String> parameters,
            List<ModifierType> modifiers,
            Set<Relationship<LeafNode>> relationships
    ) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.modifiers = modifiers;
        this.relationships = relationships;
    }

    /**
     * Gets the name of the method.
     *
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets the return type of the method.
     *
     * @return The return type.
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Gets the parameters of the method.
     *
     * @return A map of parameter names to their types.
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Gets the modifiers of the method.
     *
     * @return A list of modifiers.
     */
    public List<ModifierType> getModifiers() {
        return modifiers;
    }

    /**
     * Gets the relationships involving this method.
     *
     * @return A set of relationships.
     */
    public Set<Relationship<LeafNode>> getRelationships() {
        return relationships;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" ", modifiers.toString())).append(" ");
        sb.append(returnType).append(" ");
        sb.append(methodName).append("(");
        sb.append(String.join(", ", parameters.toString()));
        sb.append(")");

        return sb.toString();
    }
}
