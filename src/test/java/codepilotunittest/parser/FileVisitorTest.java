package codepilotunittest.parser;

import codepilotunittest.utils.PathTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import codepilotunittest.parser.factory.Parser;
import codepilotunittest.parser.factory.ParserType;
import codepilotunittest.parser.factory.ProjectParserFactory;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.NodeType;
import codepilotunittest.parser.tree.PackageNode;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static codepilotunittest.parser.tree.NodeType.CLASS;
import static codepilotunittest.parser.tree.NodeType.INTERFACE;
@DisplayName("File Visitor Test")
public class FileVisitorTest
{

    ParserType parserType = ParserType.JAVAPARSER;


    @Test
    @DisplayName("Method Return Types Test")
    void methodReturnTypesTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);
        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);

        PackageNode commandPackage = packages.get(PathTemplate.LatexEditor.COMMANDS.path);

        LeafNode addLatexCommand = commandPackage.getLeafNodes().get("AddLatexCommand");
        List<String> methodReturnTypesExpected = new ArrayList<>(Arrays.asList("Constructor", "void"));
        List<String> methodReturnTypesActual = addLatexCommand.getMethodReturnTypes();

        Collections.sort(methodReturnTypesActual);
        Collections.sort(methodReturnTypesExpected);

        assertEquals(methodReturnTypesExpected.size(), methodReturnTypesActual.size());
        assertTrue(methodReturnTypesExpected.containsAll(methodReturnTypesActual));
        assertTrue(methodReturnTypesActual.containsAll(methodReturnTypesExpected));
    }


    @Test
    @DisplayName("Method Parameter Types Test")
    void methodParameterTypesTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);
        PackageNode commandPackage = packages.get(PathTemplate.LatexEditor.COMMANDS.path);

        LeafNode     addLatexCommand          = commandPackage.getLeafNodes().get("AddLatexCommand");
        List<String> methodParameterTypes     = new ArrayList<>(List.of("VersionsManager"));
        List<String> methodParameterTypesTest = addLatexCommand.getMethodParameterTypes();

        Collections.sort(methodParameterTypesTest);
        Collections.sort(methodParameterTypes);

        assertEquals(methodParameterTypes.size(), methodParameterTypesTest.size());
        assertTrue(methodParameterTypes.containsAll(methodParameterTypesTest));
        assertTrue(methodParameterTypesTest.containsAll(methodParameterTypes));
    }


    @Test
    @DisplayName("Field Types Test")
    void fieldTypesTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);
        PackageNode commandPackage = packages.get(PathTemplate.LatexEditor.COMMANDS.path);
        LeafNode     addLatexCommand = commandPackage.getLeafNodes().get("AddLatexCommand");
        List<String> fieldTypes      = new ArrayList<>(List.of("VersionsManager"));
        List<String> fieldTypesTest = addLatexCommand
                .getFields()
                .stream()
                .map(LeafNode.Field::getFieldType)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.sort(fieldTypes);

        assertEquals(fieldTypes.size(), fieldTypesTest.size());
        assertTrue(fieldTypes.containsAll(fieldTypesTest));
        assertTrue(fieldTypesTest.containsAll(fieldTypes));
    }


    @Test
    @DisplayName("Variable Types Test")
    void variableTypesTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);
        PackageNode commandPackage = packages.get(PathTemplate.LatexEditor.CONTROLLER.path);

        LeafNode     latexEditorController = commandPackage.getLeafNodes().get("LatexEditorController");
        List<String> variablesTypes        = new ArrayList<>(List.of("CommandFactory"));
        List<String> variablesTypesTest    = new ArrayList<>(latexEditorController.getVariables().values());

        Collections.sort(variablesTypesTest);
        Collections.sort(variablesTypes);

        assertEquals(variablesTypes.size(), variablesTypesTest.size());
        assertTrue(variablesTypes.containsAll(variablesTypesTest));
        assertTrue(variablesTypesTest.containsAll(variablesTypes));
    }


    @Test
    @DisplayName("Object Creation Test")
    void objectCreationTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.ParserTesting.SRC.path);
        PackageNode sourcePackage = packages.get(PathTemplate.ParserTesting.SRC.path);

        LeafNode objectCreationSample = sourcePackage.getLeafNodes().get("ObjectCreationSample");
        List<String> objectsCreatedExpected = new ArrayList<>(List.of("ImplementingClass",
                "ImplementingClass",
                "ExtensionClass",
                "HashMap[String,TestingInterface]"));
        List<String> objectsCreatedActual = objectCreationSample.getCreatedObjects();

        Collections.sort(objectsCreatedActual);
        Collections.sort(objectsCreatedExpected);

        assertEquals(objectsCreatedExpected.size(), objectsCreatedActual.size());
        assertTrue(objectsCreatedExpected.containsAll(objectsCreatedActual));
        assertTrue(objectsCreatedActual.containsAll(objectsCreatedExpected));
    }


    @Test
    @DisplayName("LeafNode Types Test")
    void leafNodeTypesTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.ParserTesting.SRC.path);
        PackageNode inheritancePackage = packages.get(PathTemplate.ParserTesting.SRC.path);

        List<LeafNode> classLeafs     = new ArrayList<>();
        classLeafs.add(inheritancePackage.getLeafNodes().get("ImplementingClass"));
        classLeafs.add(inheritancePackage.getLeafNodes().get("ExtensionClass"));

        List<LeafNode> interfaceLeafs = new ArrayList<>();
        interfaceLeafs.add(inheritancePackage.getLeafNodes().get("TestingInterface"));
        interfaceLeafs.add(inheritancePackage.getLeafNodes().get("TestingInterface2"));

        long classCount = classLeafs.stream()
                .filter(it -> it.getNodeType().equals(CLASS))
                .count();
        assertEquals(classCount, classLeafs.size());

        long interfaceCount = interfaceLeafs.stream()
                .filter(it -> it.getNodeType().equals(INTERFACE))
                .count();
        assertEquals(interfaceCount, interfaceLeafs.size());

        PackageNode sourcePackage = packages.get(PathTemplate.ParserTesting.SRC.path);
        LeafNode enumTest = sourcePackage.getLeafNodes().get("EnumSample");
        assertEquals(NodeType.ENUM, enumTest.getNodeType());

        LeafNode objectCreationTest = sourcePackage.getLeafNodes().get("ObjectCreationSample");
        assertEquals(CLASS, objectCreationTest.getNodeType());
    }

    @Test
    void inheritanceTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.ParserTesting.SRC.path);
        PackageNode inheritancePackage  = packages.get(PathTemplate.ParserTesting.SRC.path);

        LeafNode implementingClass = inheritancePackage.getLeafNodes().get("ImplementingClass");
        assertEquals(2, implementingClass.getImplementedInterfaces().size());
        assertTrue(implementingClass.getImplementedInterfaces().contains("TestingInterface"));
        assertTrue(implementingClass.getImplementedInterfaces().contains("TestingInterface2"));

        assertEquals("ExtensionClass", implementingClass.getBaseClass());
    }


    @Test
    void innerMembersTest()
    {
        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.ParserTesting.SRC.path);
        PackageNode sourcePackage = packages.get(PathTemplate.ParserTesting.SRC.path);

        LeafNode innerClassSample = sourcePackage.getLeafNodes().get("InnerClassSample");

        assertEquals(innerClassSample.getInnerClasses().size(), 1);
        assertEquals("InnerClass", innerClassSample.getInnerClasses().get(0).getNodeName());

        assertEquals(innerClassSample.getRecords().size(), 1);
        assertEquals("RecordSample", innerClassSample.getRecords().get(0));
    }


    @Test
    void importsTest()
    {
        List<String> expectedImports = List.of("javax.swing.JOptionPane",
                "model.strategies.StableVersionsStrategy",
                "model.strategies.VersionsStrategy",
                "model.strategies.VolatileVersionsStrategy",
                "view.LatexEditorView");

        Parser parser = ProjectParserFactory.createProjectParser(parserType);

        Map<Path, PackageNode> packages = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);
        PackageNode commandPackage = packages.get(PathTemplate.LatexEditor.MODEL.path);
        LeafNode     versionsManager = commandPackage.getLeafNodes().get("VersionsManager");
        List<String> imports         = versionsManager.getImports();
        assertEquals(expectedImports, imports);
    }
}
