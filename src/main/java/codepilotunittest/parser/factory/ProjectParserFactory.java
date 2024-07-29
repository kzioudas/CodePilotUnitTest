package codepilotunittest.parser.factory;

import codepilotunittest.parser.ProjectParser;
import codepilotunittest.parser.factory.ParserType;

public class ProjectParserFactory
{

    public static Parser createProjectParser(ParserType parserType)
    {
        if (parserType.equals(ParserType.JDT))
        {
            throw new RuntimeException();
        }
        return new ProjectParser();
    }
}
