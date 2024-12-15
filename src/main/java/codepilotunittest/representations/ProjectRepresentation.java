package codepilotunittest.representations;

import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;

import java.util.List;
import java.util.Set;

/**
 * Represents the structure of a Java project, including its classes and relationships.
 */
public class ProjectRepresentation implements SrcElement {
    private final String projectName;
    private final List<ClassRepresentation> classes;
    private final Set<Relationship<PackageNode>> relationships;

    /**
     * Constructor for ProjectRepresentation.
     *
     * @param projectName The name of the project.
     * @param classes The list of classes in the project.
     * @param relationships The relationships between project elements.
     */
    public ProjectRepresentation(
            String projectName,
            List<ClassRepresentation> classes,
            Set<Relationship<PackageNode>> relationships
    ) {
        this.projectName = projectName;
        this.classes = classes;
        this.relationships = relationships;
    }

    /**
     * Gets the name of the project.
     *
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the list of classes in the project.
     *
     * @return A list of class representations.
     */
    public List<ClassRepresentation> getClasses() {
        return classes;
    }

    /**
     * Gets the relationships between project elements.
     *
     * @return A set of relationships.
     */
    public Set<Relationship<PackageNode>> getRelationships() {
        return relationships;
    }

    /**
     * Finds a class by its name in the project.
     *
     * @param className The name of the class to find.
     * @return The class representation if found.
     * @throws ClassNotFoundException If no class with the given name is found.
     */
    public ClassRepresentation findClass(String className) throws ClassNotFoundException {
        return classes.stream()
                .filter(classRepresentation -> classRepresentation.getClassName().equals(className))
                .findFirst()
                .orElseThrow(() -> new ClassNotFoundException("Class with name " + className + " not found."));
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
