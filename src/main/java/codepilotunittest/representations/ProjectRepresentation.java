package codepilotunittest.representations;


import codepilotunittest.interfaces.SrcElement;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Set;

public class ProjectRepresentation implements SrcElement {
    private String projectName;
    private List<ClassRepresentation> classes;
    private Set<Relationship<PackageNode>> relationships;

    public ProjectRepresentation(String projectName, List<ClassRepresentation> classes, Set<Relationship<PackageNode>> relationships) {
        this.projectName = projectName;
        this.classes = classes;
        this.relationships = relationships;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<ClassRepresentation> getClasses() {
        return classes;
    }

    public Set<Relationship<PackageNode>> getRelationships() {
        return relationships;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project: ").append(projectName).append("\n\n");

        for (ClassRepresentation classRepresentation : classes) {
            sb.append(classRepresentation).append("\n\n");
        }

        if (!relationships.isEmpty()) {
            sb.append("Project Relationships: \n");
            for (Relationship<PackageNode> relationship : relationships) {
                sb.append("  ").append(relationship.getType()).append(" -> ").append(relationship.getTarget().getPath()).append("\n");
            }
        }

        return sb.toString();
    }
}
