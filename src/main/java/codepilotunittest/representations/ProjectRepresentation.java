package codepilotunittest.representations;


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

    public ClassRepresentation findClass(String className) throws ClassNotFoundException {
        for (ClassRepresentation classRepresentation : classes) {
            if (classRepresentation.getClassName().equals(className)) {
                return classRepresentation;
            }
        }
        throw new ClassNotFoundException("Class with name " + className + " not found.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project: ").append(projectName).append("\n\n");

        for (ClassRepresentation classRepresentation : classes) {
            sb.append(classRepresentation.toString()).append("\n\n");
        }
        return sb.toString();
    }
}
