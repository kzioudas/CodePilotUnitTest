package codepilotunittest.directives;



//import codepilotunittest.testcases.HappyPathTestCase;
//import codepilotunittest.testcases.RainyDayTestCase;
//import codepilotunittest.testcases.TestCase;
//import java.util.List;

public class DirectiveFactory {
    public static Directive createDirective(String directive) {

        switch (directive) {
            case "1":
                //return new RainyDayTestCase(testName, classToTest, methodToTest, directives);
            case "2":
                //return new HappyPathTestCase(testName, classToTest, methodToTest, directives);
            // Add more directive types as needed
            default:
                throw new IllegalArgumentException("Unknown directive type: " + directive);
        }
    }
}
