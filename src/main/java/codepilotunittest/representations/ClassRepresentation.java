package codepilotunittest.representations;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.NodeType;
import codepilotunittest.parser.tree.Relationship;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Java class, including its methods, modifiers, interfaces, and relationships.
 */
public class ClassRepresentation implements SrcElement {
    private final String className;
    private final List<NodeType> modifiers;
    private final List<String> interfaces;
    private final List<MethodRepresentation> methods;
    private final Set<Relationship<LeafNode>> relationships;
    private final Path path;

    /**
     * Constructor for ClassRepresentation.
     *
     * @param className     The name of the class.
     * @param modifiers     The list of modifiers (e.g., public, abstract) for the class.
     * @param interfaces    The list of interfaces implemented by the class.
     * @param methods       The list of methods defined in the class.
     * @param relationships The relationships involving this class (e.g., inheritance, association).
     * @param path
     */
    public ClassRepresentation(
            String className,
            List<NodeType> modifiers,
            List<String> interfaces,
            List<MethodRepresentation> methods,
            Set<Relationship<LeafNode>> relationships,
            Path path) {
        this.className = className;
        this.modifiers = modifiers;
        this.interfaces = interfaces;
        this.methods = methods;
        this.relationships = relationships;
        this.path = path;
    }

    /**
     * Gets the name of the class.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    public Path getPath() {
        return path;
    }
    /**
     * Gets the list of modifiers applied to the class.
     *
     * @return A list of modifiers.
     */
    public List<NodeType> getModifiers() {
        return modifiers;
    }

    /**
     * Gets the list of interfaces implemented by the class.
     *
     * @return A list of interface names.
     */
    public List<String> getInterfaces() {
        return interfaces;
    }

    /**
     * Gets all methods declared in the class.
     *
     * @return A list of method representations.
     */
    public List<MethodRepresentation> getMethods() {
        return methods;
    }

    /**
     * Gets all constructors defined in the class.
     *
     * @return A list of constructor representations.
     */
    public List<MethodRepresentation> getConstructors() {
        List<MethodRepresentation> constructors = new ArrayList<>();
        for (MethodRepresentation method : methods) {
            if ("Constructor".equals(method.getReturnType())) {
                constructors.add(method);
            }
        }
        return constructors;
    }

    /**
     * Gets the relationships involving this class.
     *
     * @return A set of relationships.
     */
    public Set<Relationship<LeafNode>> getRelationships() {
        return relationships;
    }

    /**
     * Finds a method by its name in the class.
     *
     * @param methodName The name of the method to find.
     * @return The method representation if found.
     * @throws MethodNotFoundException If no method with the given name is found.
     */
    public MethodRepresentation findMethod(String methodName) throws MethodNotFoundException {
        return methods.stream()
                .filter(method -> method.getMethodName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new MethodNotFoundException("Method with name " + methodName + " not found."));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" ", modifiers.toString())).append(" ");
        sb.append("class ").append(className);

        if (!interfaces.isEmpty()) {
            sb.append(" implements ").append(String.join(", ", interfaces));
        }

        sb.append(" {\n");

        for (MethodRepresentation method : methods) {
            sb.append("  ").append(method.toString()).append("\n");
        }

        sb.append("}");
        sb.append(getPath().toString());
        return sb.toString();
    }
}
