package codepilotunittest.parser;

import codepilotunittest.utils.PathTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import codepilotunittest.parser.factory.Parser;
import codepilotunittest.parser.factory.ParserType;
import codepilotunittest.parser.factory.ProjectParserFactory;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectParserTest
{

    ParserType parserType = ParserType.JAVAPARSER;

    public static final List<Path> SOURCES_SUB_PACKAGES   = new ArrayList<>(Arrays.asList(PathTemplate.LatexEditor.CONTROLLER.path,
                                                                                          PathTemplate.LatexEditor.MODEL.path,
                                                                                          PathTemplate.LatexEditor.VIEW.path));

    public static final List<Path> VIEWS_LEAF_NODES       = new ArrayList<>(Arrays.asList(PathTemplate.LatexEditor.CHOOSE_TEMPLATE.path,
                                                                                          PathTemplate.LatexEditor.LATEX_EDITOR.path,
                                                                                          PathTemplate.LatexEditor.MAIN_WINDOW.path,
                                                                                          PathTemplate.LatexEditor.OPENING_WINDOW.path));

    public static final List<Path> CONTROLLERS_LEAF_NODES = new ArrayList<>(List.of(PathTemplate.LatexEditor.LATEX_EDITOR_CONTROLLER.path));

    public static final List<Path> STRATEGIES_LEAF_NODES  = new ArrayList<>(Arrays.asList(PathTemplate.LatexEditor.STABLE_VERSIONS_STRATEGY.path,
                                                                                          PathTemplate.LatexEditor.VERSIONS_STRATEGY.path,
                                                                                          PathTemplate.LatexEditor.VOLATILE_VERSIONS_STRATEGY.path,
                                                                                          PathTemplate.LatexEditor.VERSIONS_STRATEGY_FACTORY.path));
    public static final List<Path> MODELS_LEAF_NODES      = new ArrayList<>(Arrays.asList(PathTemplate.LatexEditor.DOCUMENT.path,
                                                                                          PathTemplate.LatexEditor.DOCUMENT_MANAGER.path,
                                                                                          PathTemplate.LatexEditor.VERSIONS_MANAGER.path));

    public static final List<Path> COMMANDS_LEAF_NODES    = new ArrayList<>(Arrays.asList(PathTemplate.LatexEditor.ADD_LATEX_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.COMMAND.path,
                                                                                          PathTemplate.LatexEditor.COMMAND_FACTORY.path,
                                                                                          PathTemplate.LatexEditor.CREATE_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.EDIT_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.LOAD_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.SAVE_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.CHANGE_VERSIONS_STRATEGY_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.DISABLE_VERSIONS_MANAGER_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.ENABLE_VERSIONS_MANAGE_COMMAND.path,
                                                                                          PathTemplate.LatexEditor.ROLLBACK_TO_PREVIOUS_VERSION_COMMAND.path));

    @Test
    void parsingTest()
    {

        Parser parser = ProjectParserFactory.createProjectParser(parserType);
        Map<Path, PackageNode> packageNodes = parser.parseSourcePackage(PathTemplate.LatexEditor.SRC.path);

        PackageNode controllerPackage = packageNodes.get(PathTemplate.LatexEditor.CONTROLLER.path);
        List<Path> testingLeafNodes = new ArrayList<>();
        Assertions.assertEquals(PathTemplate.LatexEditor.CONTROLLER.path, controllerPackage.getPath());

        Assertions.assertEquals(PathTemplate.LatexEditor.SRC.path, controllerPackage.getParentNode().getPath());

        assertTrue(controllerPackage.isValid(), "message");
        Map<Path, PackageNode> subNodes = controllerPackage.getSubNodes();
        Assertions.assertEquals(PathTemplate.LatexEditor.COMMANDS.path, subNodes.get(PathTemplate.LatexEditor.COMMANDS.path).getPath());

        for (LeafNode l : controllerPackage.getLeafNodes().values())
        {
            testingLeafNodes.add(l.getPath());
            Assertions.assertEquals(l.getParentNode().getPath(), PathTemplate.LatexEditor.CONTROLLER.path);
        }
        Collections.sort(testingLeafNodes);
        Collections.sort(CONTROLLERS_LEAF_NODES);

        assertEquals(CONTROLLERS_LEAF_NODES.size(), testingLeafNodes.size());
        assertTrue(CONTROLLERS_LEAF_NODES.containsAll(testingLeafNodes));
        assertTrue(testingLeafNodes.containsAll(CONTROLLERS_LEAF_NODES));

        testingLeafNodes.clear();

        PackageNode commandsPackage = packageNodes.get(PathTemplate.LatexEditor.COMMANDS.path);
        Assertions.assertEquals(PathTemplate.LatexEditor.COMMANDS.path, commandsPackage.getPath());
        Assertions.assertEquals(PathTemplate.LatexEditor.CONTROLLER.path, commandsPackage.getParentNode().getPath());

        assertTrue(commandsPackage.isValid());
        subNodes = commandsPackage.getSubNodes();
        assertEquals(0, subNodes.size());
        for (LeafNode l : commandsPackage.getLeafNodes().values())
        {
            testingLeafNodes.add(l.getPath());
            Assertions.assertEquals(l.getParentNode().getPath(), PathTemplate.LatexEditor.COMMANDS.path);
        }
        Collections.sort(testingLeafNodes);
        Collections.sort(COMMANDS_LEAF_NODES);

        assertEquals(COMMANDS_LEAF_NODES.size(), testingLeafNodes.size());
        assertTrue(COMMANDS_LEAF_NODES.containsAll(testingLeafNodes));
        assertTrue(testingLeafNodes.containsAll(COMMANDS_LEAF_NODES));

        testingLeafNodes.clear();

        PackageNode modelPackage = packageNodes.get(PathTemplate.LatexEditor.MODEL.path);
        Assertions.assertEquals(PathTemplate.LatexEditor.MODEL.path, modelPackage.getPath());

        Assertions.assertEquals(PathTemplate.LatexEditor.SRC.path, modelPackage.getParentNode().getPath());

        assertTrue(modelPackage.isValid());
        subNodes = modelPackage.getSubNodes();
        Assertions.assertEquals(PathTemplate.LatexEditor.STRATEGIES.path, subNodes.get(PathTemplate.LatexEditor.STRATEGIES.path).getPath());

        for (LeafNode l : modelPackage.getLeafNodes().values())
        {
            Assertions.assertEquals(l.getParentNode().getPath(), PathTemplate.LatexEditor.MODEL.path);
            testingLeafNodes.add(l.getPath());
        }
        Collections.sort(testingLeafNodes);
        Collections.sort(MODELS_LEAF_NODES);

        assertEquals(MODELS_LEAF_NODES.size(), testingLeafNodes.size());
        assertTrue(MODELS_LEAF_NODES.containsAll(testingLeafNodes));
        assertTrue(testingLeafNodes.containsAll(MODELS_LEAF_NODES));

        testingLeafNodes.clear();

        PackageNode strategiesPackage = packageNodes.get(PathTemplate.LatexEditor.STRATEGIES.path);
        Assertions.assertEquals(PathTemplate.LatexEditor.STRATEGIES.path, strategiesPackage.getPath());
        Assertions.assertEquals(PathTemplate.LatexEditor.MODEL.path, strategiesPackage.getParentNode().getPath());
        assertTrue(strategiesPackage.isValid());
        subNodes = strategiesPackage.getSubNodes();
        assertEquals(0, subNodes.size());
        for (LeafNode l : strategiesPackage.getLeafNodes().values())
        {
            Assertions.assertEquals(l.getParentNode().getPath(), PathTemplate.LatexEditor.STRATEGIES.path);
            testingLeafNodes.add(l.getPath());
        }
        Collections.sort(testingLeafNodes);
        Collections.sort(STRATEGIES_LEAF_NODES);

        assertEquals(STRATEGIES_LEAF_NODES.size(), testingLeafNodes.size());
        assertTrue(STRATEGIES_LEAF_NODES.containsAll(testingLeafNodes));
        assertTrue(testingLeafNodes.containsAll(STRATEGIES_LEAF_NODES));

        testingLeafNodes.clear();

        PackageNode viewPackage = packageNodes.get(PathTemplate.LatexEditor.VIEW.path);
        Assertions.assertEquals(PathTemplate.LatexEditor.VIEW.path, viewPackage.getPath());
        Assertions.assertEquals(PathTemplate.LatexEditor.SRC.path, viewPackage.getParentNode().getPath());
        assertTrue(viewPackage.isValid());
        subNodes = viewPackage.getSubNodes();
        assertEquals(0, subNodes.size());
        for (LeafNode l : viewPackage.getLeafNodes().values())
        {
            Assertions.assertEquals(l.getParentNode().getPath(), PathTemplate.LatexEditor.VIEW.path);
            testingLeafNodes.add(l.getPath());
        }

        Collections.sort(testingLeafNodes);
        Collections.sort(VIEWS_LEAF_NODES);

        assertEquals(VIEWS_LEAF_NODES.size(), testingLeafNodes.size());
        assertTrue(VIEWS_LEAF_NODES.containsAll(testingLeafNodes));
        assertTrue(testingLeafNodes.containsAll(VIEWS_LEAF_NODES));

        testingLeafNodes.clear();

        PackageNode sourcePackage = packageNodes.get(PathTemplate.LatexEditor.SRC.path);
        Assertions.assertEquals(PathTemplate.LatexEditor.SRC.path, sourcePackage.getPath());

        assertEquals(Paths.get(""), sourcePackage.getParentNode().getPath());
        assertFalse(sourcePackage.isValid());
        subNodes = sourcePackage.getSubNodes();
        List<Path> testingSubPackages = subNodes.values().stream()
                .map(PackageNode::getPath)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.sort(SOURCES_SUB_PACKAGES);

        assertEquals(SOURCES_SUB_PACKAGES.size(), testingSubPackages.size());
        assertTrue(SOURCES_SUB_PACKAGES.containsAll(testingSubPackages));
        assertTrue(testingSubPackages.containsAll(SOURCES_SUB_PACKAGES));
        assertEquals(0, sourcePackage.getLeafNodes().size());
    }
}
