package codepilotunittest.parser.tree;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeafNode {
	private final Path path;
	private final String nodeName;
	private final NodeType nodeType;
	private final String baseClass;
	private final PackageNode parentNode;
	private final List<String> implementedInterfaces;
	private final List<Method> methods;
	private final List<Field> fields;
	private final Map<String, String> variables;
	private final List<String> imports;
	private final List<String> records;
	private final List<LeafNode> innerClasses;
	private final List<String> innerEnums;
	private final List<String> createdObjects;

	// Constructor
	public LeafNode(Path path,
					String nodeName,
					NodeType nodeType,
					String baseClass,
					PackageNode parentNode,
					List<String> implementedInterfaces,
					List<Method> methods,
					List<Field> fields,
					Map<String, String> variables,
					List<String> imports,
					List<String> records,
					List<LeafNode> innerClasses,
					List<String> innerEnums,
					List<String> createdObjects) {
		this.path = path;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.baseClass = baseClass;
		this.parentNode = parentNode;
		this.implementedInterfaces = implementedInterfaces;
		this.methods = methods;
		this.fields = fields;
		this.variables = variables;
		this.imports = imports;
		this.records = records;
		this.innerClasses = innerClasses;
		this.innerEnums = innerEnums;
		this.createdObjects = createdObjects;
	}

	// Getters
	public Path getPath() {
		return path;
	}

	public String getNodeName() {
		return nodeName;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public String getBaseClass() {
		return baseClass;
	}

	public PackageNode getParentNode() {
		return parentNode;
	}

	public List<String> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public List<String> getImports() {
		return imports;
	}

	public List<String> getRecords() {
		return records;
	}

	public List<LeafNode> getInnerClasses() {
		return innerClasses;
	}

	public List<String> getInnerEnums() {
		return innerEnums;
	}

	public List<String> getCreatedObjects() {
		return createdObjects;
	}

	// Method from the record
	public List<String> getMethodReturnTypes() {
		return methods
				.stream()
				.map(Method::getMethodReturnType)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public List<String> getMethodParameterTypes() {
		return methods
				.stream()
				.flatMap(it -> it.getParameters().values().stream())
				.collect(Collectors.toCollection(ArrayList::new));
	}

	// Nested class: Method
	public static class Method {
		private final String methodName;
		private final String returnType;
		private final ModifierType modifierType;
		private final Map<String, String> parameters;

		public Method(String methodName, String returnType, ModifierType modifierType, Map<String, String> parameters) {
			this.methodName = methodName;
			this.returnType = returnType;
			this.modifierType = modifierType;
			this.parameters = parameters;
		}

		// Getters
		public String getMethodName() {
			return methodName;
		}

		public String getMethodReturnType() {
			return returnType;
		}

		public ModifierType getMethodModifierType() {
			return modifierType;
		}

		public Map<String, String> getParameters() {
			return parameters;
		}
	}

	// Nested class: Field
	public static class Field {
		private final String fieldNames;
		private final String fieldType;
		private final ModifierType modifierType;

		public Field(String fieldNames, String fieldType, ModifierType modifierType) {
			this.fieldNames = fieldNames;
			this.fieldType = fieldType;
			this.modifierType = modifierType;
		}

		// Getters
		public String getFieldNames() {
			return fieldNames;
		}

		public String getFieldType() {
			return fieldType;
		}

		public ModifierType getModifierType() {
			return modifierType;
		}
	}
}
