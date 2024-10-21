package codepilotunittest.parser;

import codepilotunittest.parser.factory.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import codepilotunittest.parser.ProjectParser;
import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProjectParser implements Parser
{
    private static final Logger logger = LogManager.getLogger(ProjectParser.class);

    private final Map<Path, PackageNode> packageNodes;


    public ProjectParser()
    {
        this.packageNodes = new HashMap<>();
    }


    @Override
    public Map<Path, PackageNode> parseSourcePackage(Path sourcePackagePath)
    {
        PackageNode sourcePackage = new PackageNode(sourcePackagePath);
        packageNodes.put(sourcePackage.getPath(), sourcePackage);
        parseFolder(sourcePackage);
        return packageNodes;
    }


    @Override
    public Map<LeafNode, Set<Relationship<LeafNode>>> createRelationships(Map<Path, PackageNode> packageNodes)
    {
        codepilotunittest.parser.IRelationshipIdentifier relationshipIdentifier = new RelationshipIdentifier();
        return relationshipIdentifier.createLeafNodesRelationships(packageNodes);
    }


    @Override
    public Map<PackageNode, Set<Relationship<PackageNode>>> identifyPackageNodeRelationships(Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships)
    {
        codepilotunittest.parser.IRelationshipIdentifier relationshipIdentifier = new RelationshipIdentifier();
        return relationshipIdentifier.identifyPackageNodeRelationships(leafNodeRelationships);
    }


    private void parseFolder(PackageNode currentNode)
    {
        try (DirectoryStream<Path> filesStream = Files.newDirectoryStream(currentNode.getPath()))
        {
            for (Path path : filesStream)
            {
                if (Files.isDirectory(path))
                {
                    PackageNode subNode = new PackageNode(currentNode, path);
                    packageNodes.put(subNode.getPath(), subNode);
                    currentNode.getSubNodes().put(subNode.getPath(), subNode);
                    parseFolder(subNode);
                }
                else if (isJavaSourceFile(path))
                {
                    FileVisitor fileVisitor = new FileVisitor(currentNode, path);
                    LeafNode    leafNode    = fileVisitor.createAST();
                    currentNode.getLeafNodes().put(leafNode.getNodeName(), leafNode);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Failed to open a directory stream for path: {}", currentNode.getPath().toAbsolutePath());
            throw new RuntimeException(e);
        }
    }


    private boolean isJavaSourceFile(Path filePath)
    {
        return filePath.normalize().toString().toLowerCase().endsWith(".java");
    }

}
