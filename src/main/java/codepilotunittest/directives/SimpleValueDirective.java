package codepilotunittest.directives;


/**
 * Directive for a simple value (e.g., "par value 5").
 */
public class SimpleValueDirective implements Directive {

    private final String parameterName;
    private final Object parameterValue;

    public SimpleValueDirective(String parameterName, Object parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String getDirectiveType() {
        return "value";
    }

    //todo fix that
    /**
     * @return
     */
    //ToDo for other types (String,int etc)
    @Override
    public String generateAssertion() {
        return "assertTrue(" + parameterName + ".equals(" + parameterValue + ");";
    }

    public Object getParameterValue() {
        return parameterValue;
    }


    public String toString(){
        return "SimpleValueDirective [parameterName=" + parameterName + ", parameterValue=" + parameterValue + "]" + generateAssertion();
    }
    @Override
    public boolean validate(Object value) {
        return value.equals(parameterValue);
    }
}

