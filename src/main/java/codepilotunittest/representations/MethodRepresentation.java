package codepilotunittest.representations;


import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.ModifierType;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MethodRepresentation implements SrcElement {
    private String methodName;
    private String returnType;
    private Map<String, String> parameters;
    private List<ModifierType> modifiers;
    private Set<Relationship<LeafNode>> relationships;

    public MethodRepresentation(String methodName, String returnType, Map<String, String> parameters, List<ModifierType> modifiers, Set<Relationship<LeafNode>> relationships, List<String> testAnnotations) {
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

    public Map<String, String> getParameters() {
        return parameters;
    }

    public List<ModifierType> getModifiers() {
        return modifiers;
    }

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
