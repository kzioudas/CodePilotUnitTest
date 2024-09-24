classDiagram
direction BT
class ClassRepresentation {
  + ClassRepresentation(String, List~NodeType~, List~String~, List~MethodRepresentation~, Set~Relationship~LeafNode~~, List~String~) 
  - List~NodeType~ modifiers
  - List~String~ interfaces
  - List~MethodRepresentation~ methods
  - String className
  - Set~Relationship~LeafNode~~ relationships
  + toString() String
  + findMethod(String) MethodRepresentation
   String className
   List~NodeType~ modifiers
   List~MethodRepresentation~ methods
   Set~Relationship~LeafNode~~ relationships
   List~String~ interfaces
}
class ConstructorVisitor {
  + ConstructorVisitor() 
  + visit(ConstructorDeclaration, Void) void
}
class DemoApp {
  + DemoApp() 
  + setUp() void
  + main(String[]) void
}
class Directive {
<<Interface>>

}
class DirectiveFactory {
  + DirectiveFactory() 
  + createDirective(String) Directive
}
class DirectiveParser {
  + DirectiveParser(String) 
  + parse() Map~String, String~
}
class EnumVisitor {
  + EnumVisitor() 
  + visit(EnumDeclaration, Void) void
}
class FieldVisitor {
  + FieldVisitor() 
  + visit(FieldDeclaration, Void) void
}
class FileVisitor {
  + FileVisitor(PackageNode, Path) 
  - populateCreatedObjects() List~String~
  + createAST() LeafNode
  - filterAssignedCreatedObjects(List~String~, List~String~) List~String~
  - getType(String) String
}
class HappyPathTestCase {
  + HappyPathTestCase(String, String, String, List~String~) 
  - String methodToTest
  - String testType
  - List~String~ directives
  - String testName
  - String classToTest
   List~String~ directives
   String classToTest
   String methodToTest
   String testName
   String testType
}
class IRelationshipIdentifier {
<<Interface>>
  + createLeafNodesRelationships(Map~Path, PackageNode~) Map~LeafNode, Set~Relationship~LeafNode~~~
  + identifyPackageNodeRelationships(Map~LeafNode, Set~Relationship~LeafNode~~~) Map~PackageNode, Set~Relationship~PackageNode~~~
}
class ImportVisitor {
  + ImportVisitor() 
  + visit(ImportDeclaration, Void) void
}
class InheritanceVisitor {
  + InheritanceVisitor() 
  + visit(ClassOrInterfaceDeclaration, Void) void
}
class MainEngine {
  + MainEngine(Path, String) 
  - ProjectRepresentation projectRepresentation
  - Map~LeafNode, Set~Relationship~LeafNode~~~ leafNodeRelationships
  - Map~Path, PackageNode~ packageNodes
  - Map~PackageNode, Set~Relationship~PackageNode~~~ packageNodeRelationships
  + buildProjectRepresentation(String, Map~Path, PackageNode~, Map~PackageNode, Set~Relationship~PackageNode~~~, Map~LeafNode, Set~Relationship~LeafNode~~~) ProjectRepresentation
  + buildClassRepresentation(LeafNode, Map~LeafNode, Set~Relationship~LeafNode~~~) ClassRepresentation
  + buildMethodRepresentation(Method, Set~Relationship~LeafNode~~, List~String~) MethodRepresentation
   Map~Path, PackageNode~ packageNodes
   ProjectRepresentation projectRepresentation
   Map~PackageNode, Set~Relationship~PackageNode~~~ packageNodeRelationships
   Map~LeafNode, Set~Relationship~LeafNode~~~ leafNodeRelationships
}
class MemberVisitor {
  + MemberVisitor() 
  + visit(EnumDeclaration, Void) void
  + visit(RecordDeclaration, Void) void
}
class MethodNotFoundException {
  + MethodNotFoundException(String) 
}
class MethodRepresentation {
  + MethodRepresentation(String, String, Map~String, String~, List~ModifierType~, Set~Relationship~LeafNode~~, List~String~) 
  - List~ModifierType~ modifiers
  - String returnType
  - Set~Relationship~LeafNode~~ relationships
  - String methodName
  - Map~String, String~ parameters
  + toString() String
   Map~String, String~ parameters
   String methodName
   List~ModifierType~ modifiers
   String returnType
   Set~Relationship~LeafNode~~ relationships
}
class MethodVisitor {
  + MethodVisitor() 
  + visit(MethodDeclaration, Void) void
}
class ObjectCreationVisitor {
  + ObjectCreationVisitor() 
  + visit(ObjectCreationExpr, Void) void
}
class PackageNodeCleaner {
  + PackageNodeCleaner() 
  + removeNonPackageNodes(Map~Path, PackageNode~) Map~Path, PackageNode~
  - isPackageNodeValid(PackageNode) boolean
}
class Parser {
<<Interface>>
  + parseSourcePackage(Path) Map~Path, PackageNode~
  + identifyPackageNodeRelationships(Map~LeafNode, Set~Relationship~LeafNode~~~) Map~PackageNode, Set~Relationship~PackageNode~~~
  + createRelationships(Map~Path, PackageNode~) Map~LeafNode, Set~Relationship~LeafNode~~~
}
class ParserType {
<<enumeration>>
  + ParserType() 
  + valueOf(String) ParserType
  + values() ParserType[]
}
class ProjectParser {
  + ProjectParser() 
  + parseSourcePackage(Path) Map~Path, PackageNode~
  + createRelationships(Map~Path, PackageNode~) Map~LeafNode, Set~Relationship~LeafNode~~~
  + identifyPackageNodeRelationships(Map~LeafNode, Set~Relationship~LeafNode~~~) Map~PackageNode, Set~Relationship~PackageNode~~~
  - isJavaSourceFile(Path) boolean
  - parseFolder(PackageNode) void
}
class ProjectParserFactory {
  + ProjectParserFactory() 
  + createProjectParser(ParserType) Parser
}
class ProjectRepresentation {
  + ProjectRepresentation(String, List~ClassRepresentation~, Set~Relationship~PackageNode~~) 
  - String projectName
  - List~ClassRepresentation~ classes
  - Set~Relationship~PackageNode~~ relationships
  + toString() String
  + findClass(String) ClassRepresentation
   List~ClassRepresentation~ classes
   Set~Relationship~PackageNode~~ relationships
   String projectName
}
class RainyDayTestCase {
  + RainyDayTestCase(String, String, String, List~String~) 
  - String classToTest
  - String methodToTest
  - List~String~ directives
  - String testType
  - String testName
   List~String~ directives
   String classToTest
   String methodToTest
   String testName
   String testType
}
class RelationshipIdentifier {
  + RelationshipIdentifier() 
  + identifyPackageNodeRelationships(Map~LeafNode, Set~Relationship~LeafNode~~~) Map~PackageNode, Set~Relationship~PackageNode~~~
  - isExtension(int, int) boolean
  - isRelationshipAggregation(List~String~, String) boolean
  - isDependency(int, int) boolean
  - isAssociation(int, int) boolean
  - isFieldOfTypeCollection(String, String) boolean
  - isImplementation(int, int) boolean
  - doesRelationshipExist(List~String~, String) boolean
  - isSubNode(int, int) boolean
  - getLeafNode(int) LeafNode
  - isAggregation(int, int) boolean
  - checkRelationship(int, int) void
  - doesFieldBelongToClass(String, String) boolean
  - createRelationship(int, int, RelationshipType) void
  + createLeafNodesRelationships(Map~Path, PackageNode~) Map~LeafNode, Set~Relationship~LeafNode~~~
}
class SrcElement {
<<Interface>>

}
class TestCase {
<<Interface>>
   List~String~ directives
   String classToTest
   String methodToTest
   String testName
   String testType
}
class TestCaseFactory {
  + TestCaseFactory() 
  + createTestCase(String, String, String, String, List~String~) TestCase
}
class TestCaseParser {
  + TestCaseParser() 
  # getBufferedReader(Path) BufferedReader
  - parseLineToTestCase(String) TestCase
  + parseTestCases(Path) List~TestCase~
}
class VariableVisitor {
  + VariableVisitor() 
  + visit(VariableDeclarationExpr, Void) void
}
class node32

ClassRepresentation  ..>  SrcElement 
FileVisitor  -->  ConstructorVisitor 
FileVisitor  -->  EnumVisitor 
FileVisitor  -->  FieldVisitor 
HappyPathTestCase  ..>  TestCase 
FileVisitor  -->  ImportVisitor 
FileVisitor  -->  InheritanceVisitor 
FileVisitor  -->  MemberVisitor 
MethodRepresentation  ..>  SrcElement 
FileVisitor  -->  MethodVisitor 
FileVisitor  -->  ObjectCreationVisitor 
ProjectParser  ..>  Parser 
ProjectRepresentation  ..>  SrcElement 
RainyDayTestCase  ..>  TestCase 
RelationshipIdentifier  ..>  IRelationshipIdentifier 
FileVisitor  -->  VariableVisitor 
