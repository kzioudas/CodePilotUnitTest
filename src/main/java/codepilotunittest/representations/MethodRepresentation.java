package codepilotunittest.representations;


import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Set;

public class MethodRepresentation implements SrcElement {
    private String methodName;
    private String returnType;
    private List<String> parameters;
    private List<String> modifiers;
    private Set<Relationship<LeafNode>> relationships;

    public MethodRepresentation(String methodName, String returnType, List<String> parameters, List<String> modifiers, Set<Relationship<LeafNode>> relationships, List<String> testAnnotations) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.modifiers = modifiers;
        this.relationships = relationships;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public Set<Relationship<LeafNode>> getRelationships() {
        return relationships;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" ", modifiers)).append(" ");
        sb.append(returnType).append(" ");
        sb.append(methodName).append("(");
        sb.append(String.join(", ", parameters));
        sb.append(")");

        if (!relationships.isEmpty()) {
            sb.append("\n    Relationships: ");
//            for (Relationship<LeafNode> relationship : relationships) {
//                sb.append(relationship.getType()).append(" -> ").append(relationship.getTarget().getNodeName()).append("; ");
//            }
        }

        return sb.toString();
    }
}
