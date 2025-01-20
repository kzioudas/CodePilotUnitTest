package codepilotunittest.core;

import codepilotunittest.directives.Directive;
import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;

import java.util.List;
import java.util.Map;

/**
 * Handles initialization of class instances in test cases.
 */
public class ClassInitializationHandler {

    private final ClassRepresentation classRepresentation;
    private final Directive directive;

    public ClassInitializationHandler(ClassRepresentation classRepresentation, Directive directive) {
        this.classRepresentation = classRepresentation;
        this.directive = directive;
    }

    /**
     * Generates initialization code for a class instance.
     *
     * @return The generated initialization code as a String.
     */
    public String generateInitialization() {
        StringBuilder sb = new StringBuilder();

        List<MethodRepresentation> constructors = classRepresentation.getConstructors();
        if (constructors.isEmpty()) {
            // No constructor available, assume default constructor
            sb.append("        ").append(classRepresentation.getClassName()).append(" instance = new ").append(classRepresentation.getClassName()).append("();");
            return sb.toString();
        }

        // Use the first available constructor
        MethodRepresentation constructor = constructors.get(0);
        Map<String, String> constructorParams = constructor.getParameters();

        sb.append("        ").append(classRepresentation.getClassName()).append(" instance = new ").append(classRepresentation.getClassName()).append("(");

        // Prepare constructor arguments, skipping parameters without directives
        int paramCount = 0;
        for (Map.Entry<String, String> param : constructorParams.entrySet()) {
            String paramName = param.getKey();
            String paramValue = DirectiveHandler.findDirectiveValue(directive, paramName);

            if (paramValue != null) {
                if (paramCount > 0) {
                    sb.append(", ");
                }
                sb.append(paramValue);
                paramCount++;
            }
        }

        sb.append(");");
        return sb.toString();
    }
}
