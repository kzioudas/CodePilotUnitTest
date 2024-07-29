package codepilotunittest.parser.tree;

public record Relationship<T>(T                startingNode,
                              T                endingNode,
                              RelationshipType relationshipType) {}
