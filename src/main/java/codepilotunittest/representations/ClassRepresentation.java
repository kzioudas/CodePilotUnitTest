package codepilotunittest.representations;



import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.NodeType;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Set;

public class ClassRepresentation implements SrcElement {
    private String className;
    private List<NodeType> modifiers;
    private List<String> interfaces;
    private List<MethodRepresentation> methods;
    private Set<Relationship<LeafNode>> relationships;

    public ClassRepresentation(String className, List<NodeType> modifiers, List<String> interfaces, List<MethodRepresentation> methods, Set<Relationship<LeafNode>> relationships, List<String> classTestAnnotations) {
        this.className = className;
        this.modifiers = modifiers;
        this.interfaces = interfaces;
        this.methods = methods;
        this.relationships = relationships;
    }

    public String getClassName() {
        return className;
    }

    public List<NodeType> getModifiers() {
        return modifiers;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public List<MethodRepresentation> getMethods() {
        return methods;
    }

    public Set<Relationship<LeafNode>> getRelationships() {
        return relationships;
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
        return sb.toString();
    }
}

