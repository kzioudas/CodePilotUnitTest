package codepilotunittest.testgeneration;

import codepilotunittest.representations.ClassRepresentation;
import codepilotunittest.representations.MethodRepresentation;
import codepilotunittest.representations.ProjectRepresentation;

public class ParameterGenerator {
    private ProjectRepresentation projectRepresentation;
    private ClassRepresentation classRepresentation;
    private MethodRepresentation methodRepresentation;
    public ParameterGenerator() {}

    public ParameterGenerator(ProjectRepresentation projectRepresentation){
        this.projectRepresentation = projectRepresentation;
    }


    public String generateParameter(String parameterType, String parameterName, String parameterValue) {
        String result = "";

        switch (parameterType) {
            case "String":
                result = parameterType + " " + parameterName + " = \"" + parameterValue + "\";";
                break;
            case "int":
            case "Integer":
                result = parameterType + " " + parameterName + " = " + parameterValue + ";";
                break;
            case "double":
                result = parameterType + " " + parameterName + " = " + parameterValue + "d;";
                break;
            case "float":
                result = parameterType + " " + parameterName + " = " + parameterValue + "f;";
                break;
            case "boolean":
                result = parameterType + " " + parameterName + " = " + Boolean.parseBoolean(parameterValue) + ";";
                break;
            case "char":
                result = parameterType + " " + parameterName + " = '" + parameterValue.charAt(0) + "';";
                break;
            case "long":
                result = parameterType + " " + parameterName + " = " + parameterValue + "L;";
                break;
            case "List":
                result = "List<String> " + parameterName + " = Arrays.asList(" + parameterValue + ");";
                break;
            default:
                // Try to handle other uncommon or custom types using reflection
                try {
                    result = handleCustomObject(parameterType, parameterName, parameterValue);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }

        return result;
    }
    //ToDo Fix That
    private String handleCustomObject(String parameterType, String parameterName, String parameterValue) throws ClassNotFoundException {
        // Attempt to generate instantiation code for uncommon or custom types
        try {
            Class<?> clazz = projectRepresentation.findClass(parameterType).getClass();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Here we assume the custom object has a constructor that takes a String
        return parameterType + " " + parameterName + " = new " + parameterType + "(\"" + parameterValue + "\");";

    }
}

