package codepilotunittest.parser.tree;

public class Relationship<T> {
    private final T startingNode;
    private final T endingNode;
    private final RelationshipType relationshipType;

    // Constructor
    public Relationship(T startingNode, T endingNode, RelationshipType relationshipType) {
        this.startingNode = startingNode;
        this.endingNode = endingNode;
        this.relationshipType = relationshipType;
    }

    // Getters
    public T getStartingNode() {
        return startingNode;
    }

    public T getEndingNode() {
        return endingNode;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    // Other potential logic methods, e.g.:
    public boolean isSameRelationship(T otherStartingNode, T otherEndingNode) {
        return this.startingNode.equals(otherStartingNode) && this.endingNode.equals(otherEndingNode);
    }
}
