# CodePilotUnitTest


## Packages

### `com.example.codepilotunittest.annotations`
This package contains custom annotations used throughout the project for marking and configuring test cases.

- **Annotations:**
    - `NotNull`: Ensures that a parameter is not null.
    - `ParameterRange`: Specifies the range of acceptable values for a parameter.
    - `TestConfig`: Configures test cases with expected exceptions and other settings.

### `com.example.codepilotunittest.core`
This package provides the core functionality of the testing framework.

- **Core Classes:**
    - `JUnitTest`: Represents a JUnit test with a methodRepresentation name, input parameters, and expected output.
    - `JUnitTestGenerator`: Generates JUnit test classes from the provided test cases.
    - `Parameter`: Represents a parameter with a name and type.
    - `TestCase`: Represents a single test case with parameters and expected results.
    - `TestCases`: Manages a collection of `TestCase` instances.
    - `User`: Facilitates the provision of methods, classes, or projects and testcases for testing.

### `com.example.codepilotunittest.interfaces`
This package defines the interfaces used for testcases and test case generation.

- **Interfaces:**
    - `Directive`: Defines a methodRepresentation to check certain conditions.
    - `TestCaseGenerator`: Defines a methodRepresentation to generate test cases based on input and testcases.

### `com.example.codepilotunittest.strategies`
This package includes various testing strategies used to generate different types of test cases.

- **Testing Strategies:**
    - `BoundaryValueTestingStrategy`: Generates test cases to test boundary values.
    - `DecisionBasedTestingStrategy`: Generates test cases based on decision-making logic.
    - `EquivalencePartitioningStrategy`: Generates test cases based on equivalence partitioning.
    - `ExceptionTestingStrategy`: Generates test cases to test exception handling.
    - `HappyDayTestingStrategy`: Generates test cases for typical (happy path) scenarios.
    - `RainyDayTestingStrategy`: Generates test cases for atypical (rainy day) scenarios.

### `com.example.codepilotunittest.testcasegeneration`
This package focuses on generating test cases from classes, methods, and projects.

- **Test Case Generation:**
    - `Class`: Generates test cases for a given class.
    - `Method`: Generates test cases for a given methodRepresentation.
    - `Project`: Generates test cases for a collection of classes representing a project.
    - `TestCaseGeneratorFactory`: Provides factory methods to obtain appropriate `TestCaseGenerator` instances based on input.

## Relationships

- The **core** package's `JUnitTestGenerator` utilizes annotations from the **annotations** package to generate and configure test cases.
- Various classes in the **core** package interact with interfaces from the **interfaces** package to implement testing logic.
- The **strategies** package classes implement the `TestCaseGenerator` interface to provide different strategies for test case generation.
- The **testcasegeneration** package contains implementations that interact with the `TestCaseGenerator` interface and use strategies to create comprehensive test cases.


