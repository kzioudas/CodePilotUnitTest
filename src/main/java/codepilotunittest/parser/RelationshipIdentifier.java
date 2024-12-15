package codepilotunittest.parser;

import codepilotunittest.parser.tree.LeafNode;
import codepilotunittest.parser.tree.PackageNode;
import codepilotunittest.parser.tree.Relationship;
import codepilotunittest.parser.tree.RelationshipType;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RelationshipIdentifier implements IRelationshipIdentifier
{
    private final Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships;
    private final List<LeafNode>                             leafNodes;


	public RelationshipIdentifier()
    {
		leafNodes 			  = new ArrayList<>();
		leafNodeRelationships = new HashMap<>();
	}


    @Override
    public Map<LeafNode, Set<Relationship<LeafNode>>> createLeafNodesRelationships(Map<Path, PackageNode> packageNodes)
    {
        packageNodes.values().forEach(packageNode -> leafNodes.addAll(packageNode.getLeafNodes().values()));
        for (int i = 0; i < leafNodes.size(); i++)
        {
            for (int j = i + 1; j < leafNodes.size(); j++)
            {
                checkRelationship(i, j);
                checkRelationship(j, i);
            }
        }
        return leafNodeRelationships;
    }


    @Override
    public Map<PackageNode, Set<Relationship<PackageNode>>> identifyPackageNodeRelationships(Map<LeafNode, Set<Relationship<LeafNode>>> leafNodeRelationships)
    {
        Map<PackageNode, Set<Relationship<PackageNode>>> packageNodeRelationships = new HashMap<>();
        ArrayList<Relationship<LeafNode>> relationships = leafNodeRelationships
            .values()
            .stream()
            .flatMap(Set::stream)
            .collect(Collectors.toCollection(ArrayList::new));

        for (Relationship<LeafNode> relationship : relationships)
        {
            if (relationship.getStartingNode().getParentNode().equals(relationship.getEndingNode().getParentNode()))
            {
                continue;
            }
            packageNodeRelationships.computeIfAbsent(relationship.getStartingNode().getParentNode(),
                                                     packageNode -> new HashSet<>()).add(new Relationship<>(relationship.getStartingNode().getParentNode(),
                                                                                                            relationship.getEndingNode().getParentNode(),
                                                                                                            RelationshipType.DEPENDENCY));
        }
        return packageNodeRelationships;
    }


    private boolean isAssociation(int i, int j)
    {
        return doesRelationshipExist(leafNodes
                                         .get(i)
                                         .getFields()
                                         .stream()
                                         .map(LeafNode.Field::getFieldType)
                                         .collect(Collectors.toCollection(ArrayList::new)),
                                     leafNodes.get(j).getNodeName());
    }


    private boolean isAggregation(int i, int j)
    {
        return isRelationshipAggregation(leafNodes
                                             .get(i)
                                             .getFields()
                                             .stream()
                                             .map(LeafNode.Field::getFieldType)
                                             .collect(Collectors.toCollection(ArrayList::new)),
                                         leafNodes.get(j).getNodeName());
    }


    private boolean doesRelationshipExist(List<String> leafNodesTypes,
                                          String leafNodesName)
    {
        for (String leafNodesType : leafNodesTypes)
        {
            if (doesFieldBelongToClass(leafNodesType, leafNodesName))
            {
                return true;
            }
        }
        return false;
    }


    private boolean isRelationshipAggregation(List<String> leafNodesTypes,
                                              String leafNodesName)
    {
        for (String leafNodeType : leafNodesTypes)
        {
            if (isFieldOfTypeCollection(leafNodeType, leafNodesName) &&
                doesFieldBelongToClass(leafNodeType, leafNodesName))
            {
                return true;
            }
        }
        return false;
    }


    private boolean doesFieldBelongToClass(String leafNodesType,
                                           String leafNodesName)
    {
        String[] types = leafNodesType
            .replace("[", ",")
            .replace("]", ",")
            .split(",");
        for (String type : types)
        {
            if (leafNodesName.equals(type))
            {
                return true;
            }
        }
        return false;
    }


	private boolean isFieldOfTypeCollection(String s,
											String leafNodesName)
    {
		return s.contains(leafNodesName+"[") ||
			   s.startsWith("List") 	 	 ||
			   s.startsWith("ArrayList") 	 ||
			   s.startsWith("Map") 		 	 ||
			   s.startsWith("HashMap") 	 	 ||
			   s.startsWith("ArrayDeque")    ||
			   s.startsWith("Deque")	     ||
			   s.startsWith("LinkedList")    ||
			   s.startsWith("LinkedHashMap") ||
			   s.startsWith("PriorityQueue") ||
			   s.startsWith("Queue");
	}


	private void createRelationship(int 			 i,
									int 			 j,
									RelationshipType relationshipType)
    {
		leafNodeRelationships
			.computeIfAbsent(leafNodes.get(i), leafNode ->  new HashSet<>())
			.add(new Relationship<>(leafNodes.get(i),
									leafNodes.get(j),
									relationshipType));
	}


    private void checkRelationship(int i, int j)
    {
        List<String> imports = leafNodes.get(i).getImports();
        boolean isImported = imports
            .stream()
            .anyMatch(imprt -> (String.format("%s.%s",
                                              leafNodes.get(j).getParentNode().getNodeName(),
                                              leafNodes.get(j).getNodeName())).endsWith(imprt) ||
                               (String.format("%s.*",
                                              leafNodes.get(j).getParentNode().getNodeName())).endsWith(imprt));

        if (!isImported && !isSubNode(i, j))
        {
            return;
        }
        if (isDependency(i, j))
        {
            createRelationship(i, j, RelationshipType.DEPENDENCY);
        }
        if (isAggregation(i, j))
        {
            createRelationship(i, j, RelationshipType.AGGREGATION);
        }
        else if (isAssociation(i, j))
        {
            createRelationship(i, j, RelationshipType.ASSOCIATION);
        }

        if (isExtension(i, j))
        {
            createRelationship(i, j, RelationshipType.EXTENSION);
        }
        if (isImplementation(i, j))
        {
            createRelationship(i, j, RelationshipType.IMPLEMENTATION);
        }
    }


    private boolean isSubNode(int i, int j)
    {
        PackageNode node = leafNodes.get(j).getParentNode();
        while (true)
        {
            if (node.equals(leafNodes.get(i).getParentNode()))
            {
                return true;
            }

            if (node.getPath().toString().isEmpty())
            {
                return false;
            }
            node = node.getParentNode();
        }
    }


	private boolean isDependency(int i, int j)
    {
		if (doesRelationshipExist(leafNodes.get(i).getMethodParameterTypes(),
								  leafNodes.get(j).getNodeName()) 				  ||
			doesRelationshipExist(leafNodes.get(i).getMethodReturnTypes(),
								  leafNodes.get(j).getNodeName()) 				  ||
			doesRelationshipExist(new ArrayList<>(getLeafNode(i).getVariables().values()),
								  leafNodes.get(j).getNodeName())) {
			return true;
		}
		return doesRelationshipExist(leafNodes.get(i).getCreatedObjects(),
									 leafNodes.get(j).getNodeName());
	}


    private boolean isExtension(int i, int j)
    {
        // First check if any of the inner classes of i extends j.
        List<LeafNode> innerClassesI = leafNodes.get(i).getInnerClasses();
        boolean anyInnerClassExtendJ = innerClassesI.stream()
            .anyMatch(leafNode -> leafNode.getBaseClass().equals(leafNodes.get(j).getNodeName()));
        if (anyInnerClassExtendJ)
        {
            return true;
        }

        if (leafNodes.get(i).getBaseClass().isEmpty())
        {
            return false;
        }
        return leafNodes.get(i).getBaseClass().equals(leafNodes.get(j).getNodeName());
    }


    private boolean isImplementation(int i, int j)
    {
        // First check if any of the inner classes of i implements j.
        List<LeafNode> innerClassesI = leafNodes.get(i).getInnerClasses();
        boolean anyInnerClassImplementJ = innerClassesI.stream()
            .anyMatch(leafNode -> leafNode.getImplementedInterfaces().contains(leafNodes.get(j).getNodeName()));
        if (anyInnerClassImplementJ)
        {
            return true;
        }

        if (leafNodes.get(i).getImplementedInterfaces().isEmpty())
        {
            return false;
        }

        for (String implementedInterface : leafNodes.get(i).getImplementedInterfaces())
        {
            if (implementedInterface.equals(leafNodes.get(j).getNodeName()))
            {
                return true;
            }
        }
        return false;
    }


    private LeafNode getLeafNode(int i)
    {
        return leafNodes.get(i);
    }
}
