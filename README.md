# Sprockell Compiler

This software allows to compile custom programming language into machine code for the virtual CPU called Sprockell.


## Prerequisites

Make sure you have installed:

- Maven. Version 3.8 or later.
- A Java 11 or later.


## Compiling

In a terminal, run:

```
mvn compile
```


## Running

In a terminal, run:

```
mvn exec:java -Dexec.mainClass="Main"
```

This runs the `main` method of the class `Main`.

Note: `exec` does not invoke `compile`, so if changes were made to the code, `compile` needs to be invoked before `exec`. These commands can also be combined:

```
mvn compile exec:java -Dexec.mainClass="Main"
```

`mvn exec:java` is part of the maven-exec plugin. For more information about e.g. passing arguments to the main class, we refer you to the plugin documentation.

## Tests

In a terminal, run:

```
mvn test
```

This will run all test classes in the `src/test/java` directory which have names starting with "Test".

