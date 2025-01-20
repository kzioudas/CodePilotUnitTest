# CodePilotUnitTest

CodePilotUnitTest is a Java-based framework designed to automate the generation of JUnit test cases. By utilizing a combination of class representations, directives, and test case structures, the framework streamlines the process of creating robust, maintainable test cases for any Java project.

---

## Features

- **Class Representation**: Parse and represent classes, methods, and their relationships.
- **Directives**: Define expected behaviors, inputs, and outputs through a flexible directive system.
- **Test Case Types**:
  - `HappyPathTestCase`: For validating standard functionality.
  - `RainyDayTestCase`: For testing edge cases and error scenarios.
- **JUnit Test Generation**: Automatically generate test classes and methods, adhering to Java's best practices.
- **CSV-Based Test Cases**: Parse test cases from CSV files for dynamic test generation.

---

## Table of Contents

- [Usage](#usage)
- [Directory Structure](#directory-structure)
- [How It Works](#how-it-works)
  - [1. Class Representation](#1-class-representation)
  - [2. Directives](#2-directives)
  - [3. Test Case Parser](#3-test-case-parser)
  - [4. JUnit Test Generator](#4-junit-test-generator)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

---


## Usage

1. **Configure Paths**:
   Update the paths to your source package and test cases CSV file in the `DemoApp` class.

2. **Run the Framework**:
   Execute the `DemoApp` class to generate test cases dynamically:
   ```bash
   java -cp target/classes client.DemoApp
   ```

3. **Generated Test Files**:
   The generated test files will be located in the specified output directory.

---

## Directory Structure

```
CodePilotUnitTest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── client/                  # Demo application
│   │   │   ├── codepilotunittest/core/  # Core logic for test generation
│   │   │   ├── codepilotunittest/directives/ # Directive handling
│   │   │   ├── codepilotunittest/representations/ # Class and method representations
│   │   │   ├── codepilotunittest/structure/      # Test classes and methods
│   │   │   └── codepilotunittest/testcases/      # Test case types and parsing
│   ├── test/
│       └── resources/
│           ├── example-project/         # Example source code for testing
│           └── testcases.csv            # Example test cases CSV file
├── pom.xml
└── README.md
```

---

## How It Works

### 1. Class Representation
- **Classes and Methods**:
  Represented using `ClassRepresentation` and `MethodRepresentation` classes.
- **Relationships**:
  Captured using `Relationship` objects.

### 2. Directives
- Define expected behaviors, inputs, and outputs for test cases.
- Supported directive types:
  - `NullDirective`
  - `RangeDirective`
  - `SimpleValueDirective`
  - `ThrowsExceptionDirective`

### 3. Test Case Parser
- Reads a CSV file and parses test cases into `HappyPathTestCase` and `RainyDayTestCase` objects.
- Validates directives and parameter mappings.

### 4. JUnit Test Generator
- Uses `JUnitTestGenerator` to create test classes and methods.
- Test classes are generated using `TestClass` and `TestMethod` classes.

---

## Examples

### Example CSV Test Case
```
TestType|ClassName|MethodName|Parameters|ExpectedResult|ExpectedBehavior
HAPPY_PATH|MyClass|myMethod|param1:5;param2:10|15|none
RAINY_DAY|MyClass|myMethod|param1:null|IllegalArgumentException|throws
```

### Generated Test Class
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyClassTest {

    @Test
    void testMyMethod_happyPath() {
        MyClass instance = new MyClass();
        int param1 = 5;
        int param2 = 10;
        int result = instance.myMethod(param1, param2);
        assertEquals(15, result, "Expected result to equal 15");
    }

    @Test
    void testMyMethod_rainyDay() {
        MyClass instance = new MyClass();
        assertThrows(IllegalArgumentException.class, () -> instance.myMethod(null), "Expected method to throw IllegalArgumentException");
    }
}
```

---