# go-to-cpp-transpiler

## Description
This is the transpiler from **Go** source code to **C++** source code written on **Kotlin** with the use of **ANTLRv4**.

From **Go** source code it builds an AST and then performs semantic analysis (typechecking).
After that, it generates **C++** source code.

## How to use
> To use the transpiler you need to have [Java JRE](https://docs.oracle.com/goldengate/1212/gg-winux/GDRAD/java.htm#BGBFJHAB) installed on your device.

1. Clone this project
2. Run ```./gradlew build```
3. Run ```java -jar build/libs/jago.jar <path_to_go_code_file>```

The last command has the following options:
1. You can specify just the path to **Go** source code file. Then the resulting **C++** source code file will be generated in the current directory with name `output-cpp`.
    
    ```java -jar build/libs/jago.jar <path_to_go_code_file>```
2. Besides the path to **Go** source code file you can also specify the path to resulting **C++** source code file with the help of `-o` flag. If such file already exists it will be overriden.

   ```java -jar build/libs/jago.jar <path_to_go_code_file> -o <path_to_cpp_code_file>```

## Support for Go language constructs

The following Go constructs are not supported since there is no analogy in *C++*
1. Goroutines
2. Channels
3. Slices

There are also features that is added to intial **Go* grammar:
1. Function overloading.
2. `to_string(x)` function converts `x` to string. `x` must be of a base type: `int`, `float64`, `bool`, or pointer to these types.

## Development process

### Tools
* **ANTLRv4** for generating a lexer and a parser for **Go** grammar.
* **Kotlin** as the main programming language
* **Gradle** as the main build tool
* **JUnit 5** for testing the transpiler
* **ShadowJar** plugin for build a **jar** file

### Process 
1. During the **Gradle** build **ANTLRv4** generates parser for input **Go** source code.
2. Generated parser creates *Concrete Syntax Tree* of the given **Go** source code.
3. *Concrete Syntax Tree* is converted to *Abstract Syntax Tree*.
4. During the sematic analysis typechecking stage happens.
5. *Abstract Syntax Tree* is translated to **C++** source code.

### Testing
Each stage is covered by tests.
There are **76** tests in total.
